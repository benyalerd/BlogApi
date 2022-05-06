package com.example.gradleinitial.controller;

import com.example.gradleinitial.dto.request.*;
import com.example.gradleinitial.dto.response.*;
import com.example.gradleinitial.filter.Common;
import com.example.gradleinitial.model.Follow;
import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.model.Role;
import com.example.gradleinitial.model.UserToken;
import com.example.gradleinitial.repository.RoleRepository;
import com.example.gradleinitial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class userController {
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Autowired
    private Common commonService;


    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody registerUser user)
    {

        insertResponse response = new insertResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);
        try{
            var violations = validator.validate(user);
            var role = commonService.checkRole(user.getRole());
            log.info("violations = {}",violations);

            if(!violations.isEmpty() || role == null)
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else
            {
                var mapper = new ModelMapper();
                mapper.getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT);

                var requetUser = mapper.map(user, Member.class);
                requetUser.setRole(role.get());

                var newUser = userService.registerUser (requetUser);

                if(newUser == null){
                    response.setIsEror(true);
                    response.setErrorCode("002");
                    response.setErrorMsg("Existing E-mail");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

                response.setInsertId(newUser.getId());
                return ResponseEntity.ok(response);
            }
        }catch(Throwable t){
            log.error("error occur ={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody loginRequest request)
    {
        try
        {

            log.info("login Controller ...");
            log.info("email={} password={}",request.getEmail(),request.getPassword());
            var violations = validator.validate(request);
            log.info("violations = {}",violations);
            if(!violations.isEmpty()){              
                var errorMessage = violations.toString();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }

            loginResponse loginRes = userService.login(request.getEmail(),request.getPassword());
            log.info("login Controller loginRes={}", loginRes);

            if(loginRes == null)
            {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(loginRes);
        }   catch(Throwable t){

            log.error("error occur={}",t.getMessage());
            return ResponseEntity.internalServerError().body(t.getMessage());
        }
    }

    @PostMapping("set_password/{user_id}")
    public ResponseEntity<Object> setPassword(@PathVariable("user_id") Long user_id , @RequestBody setPasswordRequest newPassword)
    {
        if(commonService.checkAccessService(newPassword.getRole(), "setPassword")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        updateResponse response = new updateResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);

        try
        {
            var violations = validator.validate(newPassword);
            log.info("violations = {}",violations);

            if(!violations.isEmpty())
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else{
                Member user = userService.setPassword(user_id, newPassword.getNewPassword());
                if(user == null){
                    response.setIsEror(true);
                    response.setErrorCode("404");
                    response.setErrorMsg("not found user token");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }
            response.setUpdateId(user_id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(Throwable t){

            log.error("error occur={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @PostMapping("refresh_token")
    public ResponseEntity<Object> refreshToken(@RequestBody refreshTokenRequest refrshToken)
    {
        if(commonService.checkAccessService(refrshToken.getRole(), "refreshToken")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        updateResponse response = new updateResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);

        try
        {
            var violations = validator.validate(refrshToken);
            log.info("violations = {}",violations);
            if(!violations.isEmpty())
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else{
                UserToken user = userService.RefreshToken(refrshToken.getToken());
                if(user == null){
                    response.setIsEror(true);
                    response.setErrorCode("404");
                    response.setErrorMsg("not found user token");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(Throwable t){

            log.error("error occur={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @PostMapping("update_user/{user_id}")
    public ResponseEntity<Object> updateUser(@PathVariable("user_id") Long user_id ,@RequestBody editUserRequest newUser)
    {
        if(commonService.checkAccessService(newUser.getRole(), "editProfile")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        updateResponse response = new updateResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);

        try{

            var violations = validator.validate(newUser);
            log.info("violations = {}",violations);
            if(!violations.isEmpty())
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else
            {

                var mapper = new ModelMapper();
                mapper.getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT);

                var user = userService.editProfile(user_id,mapper.map(newUser,Member.class));
                if(user == null)
                {
                    response.setIsEror(true);
                    response.setErrorCode("404");
                    response.setErrorMsg("not found user token");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

                response.setUpdateId(user.getId());
                return ResponseEntity.ok(response);
            }
        } catch(Throwable t){

            log.error("error occur={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @PostMapping("getListFollow")
    public ResponseEntity<Object> getListFollow(@RequestBody getUserRequest request)
    {
        if(commonService.checkAccessService(request.getRole(), "getListFollow")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        ListUserResponse response = new ListUserResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);

        try{
            var violations = validator.validate(request);
            log.info("violations = {}",violations);
            if(!violations.isEmpty())
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else
            {

                var mapper = new ModelMapper();
                mapper.getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT);
                if(request.getPage() <0)
                {
                    request.setPage(0);
                }
                if(request.getLimit() <= 0)
                {
                    request.setLimit(20);
                }

                    Page<Follow> user = userService.getListFollow(request.getUserId(), request.getRole(),request.getPage(),request.getLimit());
                    List<getUserResponse> listUser = new ArrayList<>();
                    if(user != null)
                    {
                        if(request.getRole() == 1) {
                            for (int i = 0; i < user.getSize(); i++) {
                               var follower =  mapper.map(user.getContent().get(i).getFollower(), getUserResponse.class);
                                listUser.add(follower);
                            }
                        }
                        else
                        {
                            for (int i = 0; i < user.getSize(); i++) {
                                var following =  mapper.map(user.getContent().get(i).getFollowing(), getUserResponse.class);
                                listUser.add(following);
                            }
                        }

                        response.setListUser(listUser);
                        response.setTotalRecord(user.getTotalElements());
                    }


                return ResponseEntity.ok(response);
            }
        }catch(Exception t){
            log.error("error occur={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }


}
