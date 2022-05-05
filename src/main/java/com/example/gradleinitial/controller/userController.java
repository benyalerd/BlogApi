package com.example.gradleinitial.controller;

import com.example.gradleinitial.dto.request.loginRequest;
import com.example.gradleinitial.dto.request.refreshTokenRequest;
import com.example.gradleinitial.dto.request.registerUser;
import com.example.gradleinitial.dto.request.setPasswordRequest;
import com.example.gradleinitial.dto.response.insertResponse;
import com.example.gradleinitial.dto.response.loginResponse;
import com.example.gradleinitial.dto.response.updateResponse;
import com.example.gradleinitial.filter.Common;
import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.model.UserToken;
import com.example.gradleinitial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;

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
                var sb = new StringBuilder();
                for(var violation:violations)
                {
                    sb.append(violation.getMessage());
                    sb.append("\n");

                }
                var errorMessage = sb.toString();
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
    public ResponseEntity<Object> RefreshToken(@RequestBody refreshTokenRequest refrshToken)
    {
        if(commonService.checkAccessService(refrshToken.getRole(), "setPassword")) {
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

}
