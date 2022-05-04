package com.example.gradleinitial.service;

import com.example.gradleinitial.model.UserToken;
import com.example.gradleinitial.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

@Component
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserTokenRepository userTokenRepository;

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

}
