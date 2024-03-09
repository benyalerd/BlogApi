package com.example.gradleinitial.service;

import com.example.gradleinitial.dto.enumm.Role;
import com.example.gradleinitial.dto.response.loginResponse;
import com.example.gradleinitial.dto.response.userDetailResponse;
import com.example.gradleinitial.filter.Common;
import com.example.gradleinitial.model.Follow;
import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.model.UserToken;
import com.example.gradleinitial.repository.ClientRepository;
import com.example.gradleinitial.repository.FollowRepository;
import com.example.gradleinitial.repository.UserRepository;
import com.example.gradleinitial.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

@Component
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FollowRepository followRepository;

    public Member registerUser(Member user)throws Throwable{
        Member isexistingEmail = userRepository.findByEmail(user.getEmail());
        if(isexistingEmail != null){
            return null;
        }
        String salt = generateKey(20);
        String hashPassword = hashPassword(user.getPassword(),salt);
        user.setPassword(hashPassword);
        user.setSalt(salt);
        user.setIsActive(true);
        user.setCreatedBy("SYSTEM");
        Member newUser = userRepository.save(user);
        return newUser;
    }

    public loginResponse login(String email, String password)
            throws Throwable{
        log.info("login service...");
        log.info("email={} password={}",email,password);

        var user = userRepository.findByEmail(email);

        if(user != null)
        {
                String loginPassword = hashPassword(password,user.getSalt());
                log.info("Hash password={}", loginPassword);
                log.info("DB password ={}", user.getPassword());
                if(user.getPassword().equals(loginPassword)){

                    loginResponse response = new loginResponse();
                    UserToken userToken = new UserToken();

                    String apiKey =  generateKey(128);

                    userToken.setToken(apiKey);
                    response.setToken(apiKey);
                    log.info("login service apiKey={}",apiKey);

                    var clientEntity = clientRepository.findByClientName("user_token");

                    if(clientEntity != null)
                    {
                        Long consentLifetime = clientEntity.getConsentLifetime();
                        log.info("login service consentLifetime={}",consentLifetime);

                        Date startDate = Calendar.getInstance().getTime();
                        log.info("login service startDate={}",startDate); // gets a calendar using the default time zone and locale.
                        Calendar expire = Calendar.getInstance();
                        expire.add(Calendar.SECOND,consentLifetime.intValue());
                        log.info("login service expire={}",expire.getTime());
                        
                        userToken.setStartDate(startDate);
                        userToken.setExpireDate(expire.getTime());
                    }

                    UserToken updateToken = updateToken(userToken,user.getId());
                    if(updateToken == null)
                    {
                        //insert token -- first login
                        userToken.setRefClient(clientEntity);
                        userToken.setStatus(true);
                        userToken.setMember(user);

                        userTokenRepository.save(userToken);
                    }
                    return response;
            }
        }
        return null;
    }

    public Member setPassword(Long userId,String password)throws Throwable{
        var user = userRepository.findById(userId).get();
        if(user!= null)
        {
            String HashPassword  = hashPassword(password,user.getSalt());
            user.setPassword(HashPassword);
            userRepository.save(user);
        }
        return user;
    }

    public UserToken refreshToken(String token)throws Throwable{
        UserToken user = userTokenRepository.findByToken(token);
        if(user!= null)
        {
            Long consentLifetime = user.getRefClient().getConsentLifetime();
            Date currentDate = Calendar.getInstance().getTime();
            Calendar expireDate = Calendar.getInstance();
            expireDate.add(Calendar.SECOND,consentLifetime.intValue());

            user.setExpireDate(expireDate.getTime());
            user.setRefreshDateTime(currentDate);
            
            userTokenRepository.save(user);
        }
        return user;
    }

    public Member editProfile(Long userId,Member user)throws Throwable{
        var exitingUser = userRepository.findById(userId).get();
        if(exitingUser == null) return null;

        if(user.getImageprofile()!=null)exitingUser.setImageprofile(user.getImageprofile());
        if(user.getUsername()!=null)exitingUser.setUsername(user.getUsername());

        return userRepository.save(exitingUser);
    }

    public Page<Follow> getListFollow(Long userId, Long role, Integer page, Integer limit){

        if(role == 1) {
            return followRepository.findByFollowingId(userId,PageRequest.of(page, limit));
        }
        else
        {
            return followRepository.findByFollowerId(userId,PageRequest.of(page, limit));
        }
    }

    public Follow addFollow(Follow newFollow){
        return followRepository.save(newFollow);
    }

    public Follow unFollow(Long userId,Long authorId){
        var follow = followRepository.findByFollowerIdAndFollowingId(userId,authorId);
        if(follow == null) return null;

        followRepository.delete(follow);
        return follow;
    }

    public userDetailResponse getUserDetail(Long userId, Long role){
      if(role == Role.AUTHOR.getValue()){
          return userRepository.findAuthorDetailByUserId(userId);
      }
      else{
          return userRepository.findReaderDetailByUserId(userId);
      }
    }

    //<editor-fold desc="Helper Method">

    private UserToken updateToken(UserToken userToken,Long memberId){
        var Token = userTokenRepository.findByMemberId(memberId);
        if(Token == null) return Token;
        if(!userToken.getToken().isEmpty() && userToken.getToken() != null){
            Token.setToken(userToken.getToken());
        }
        if(userToken.getStartDate()  != null){
            Token.setStartDate(userToken.getStartDate());
        }
        if(userToken.getExpireDate()  != null){
            Token.setExpireDate(userToken.getExpireDate());
        }
        if(userToken.getRefreshDateTime()  != null){
            Token.setRefreshDateTime(userToken.getRefreshDateTime());
        }
        return userTokenRepository.save(Token);
    }

    public String hashPassword(String password,String salt)
            throws Throwable {
        password = password + salt;
        /* MessageDigest instance for MD5. */
        MessageDigest m = MessageDigest.getInstance("MD5");

        /* Add plain-text password bytes to digest using MD5 update() method. */
        m.update(password.getBytes());

        /* Convert the hash value into bytes */
        byte[] bytes = m.digest();

        /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
        StringBuilder s = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        /* Complete hashed password in hexadecimal format */
        String encryptedpassword = s.toString();
        return encryptedpassword;
    }


    public static String generateKey(final int keyLen){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[keyLen/8];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

    public UserToken getUserTokenByToken(String token){
        return userTokenRepository.findByToken(token);
    }

    public Boolean checkExpireToken(UserToken userToken){
        Date currentDate = Calendar.getInstance().getTime();
        if(userToken.getExpireDate().after(currentDate)){
            return false;
        }
        return true;
    }

    public Boolean checkActiveToken(UserToken userToken){
     return userToken.getStatus();
    }
    //</editor-fold>

}
