package com.example.gradleinitial.controller;


import com.example.gradleinitial.dto.request.*;
import com.example.gradleinitial.dto.response.deleteResponse;
import com.example.gradleinitial.dto.response.insertResponse;
import com.example.gradleinitial.dto.response.updateResponse;
import com.example.gradleinitial.filter.Common;
import com.example.gradleinitial.model.Article;
import com.example.gradleinitial.model.FavoriteAndShareAndView;
import com.example.gradleinitial.model.Follow;
import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.repository.FavoriteAndShareAndViewRepository;
import com.example.gradleinitial.service.ArticleService;
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
@RequestMapping("/api/article")
@Slf4j
public class articleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Validator validator;

    @Autowired
    private Common commonService;

    @PostMapping("addArticle")
    public ResponseEntity<Object> addArticle(@RequestBody addArticleRequest article)
    {
        if(!commonService.checkAccessService(article.getRole(), "addArticle")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        insertResponse response = new insertResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);
        try{
            var violations = validator.validate(article);
            log.info("violations = {}",violations);
            var category = commonService.checkCategory(article.getCategoryId()).get();
            var user = commonService.checkUser(article.getUserId()).get();
            if(!violations.isEmpty() || category == null)
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else if(user == null)
            {
                response.setIsEror(true);
                response.setErrorCode("003");
                response.setErrorMsg("user not found");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else
            {
                var mapper = new ModelMapper();
                mapper.getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT);

                var requetArticle = mapper.map(article, Article.class);
                requetArticle.setCategory(category);
                requetArticle.setAuthor(user);

                var newArticle = articleService.addArticle (requetArticle);

                if(newArticle == null){
                    response.setIsEror(true);
                    response.setErrorCode("002");
                    response.setErrorMsg("Existing E-mail");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

                response.setInsertId(newArticle.getId());
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


    @PostMapping("editArticle")
    public ResponseEntity<Object> editArticle(@RequestBody editArticleRequest article)
    {
        if(!commonService.checkAccessService(article.getRole(), "editArticle")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        updateResponse response = new updateResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);
        try{
            var violations = validator.validate(article);
            log.info("violations = {}",violations);
            var category = commonService.checkCategory(article.getCategoryId()).get();
            var user = commonService.checkUser(article.getUserId()).get();
            if(!violations.isEmpty() || (category == null&& article.getCategoryId() != 0))
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else if(user == null)
            {
                response.setIsEror(true);
                response.setErrorCode("003");
                response.setErrorMsg("user not found");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else
            {
                var mapper = new ModelMapper();
                mapper.getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT);

                var requetArticle = mapper.map(article, Article.class);
                if(article.getCategoryId() != 0) {
                    requetArticle.setCategory(category);
                }
                var updateArticle = articleService.editArticle (article.getArticleId(),requetArticle);

                if(updateArticle == null){
                    response.setIsEror(true);
                    response.setErrorCode("002");
                    response.setErrorMsg("Existing E-mail");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

                response.setUpdateId(updateArticle.getId());
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

    @PostMapping("deleteArticle/{article_id}/{role_id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("article_id") Long article_id,@PathVariable("role_id") Long role_id)
    {
        if(!commonService.checkAccessService(role_id, "deleteArticle")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        deleteResponse response = new deleteResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);
        try{
            var deleteArticle = articleService.deleteArticle(article_id);

            if(deleteArticle == null){
                response.setIsEror(true);
                response.setErrorCode("002");
                response.setErrorMsg("Existing E-mail");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            response.setDeleteId(deleteArticle.getId());
            return ResponseEntity.ok(response);

        }catch(Throwable t){
            log.error("error occur ={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("subscribeArticle")
    public ResponseEntity<Object> subscribeArticle(@RequestBody subscribeArticleRequest subscribe)
    {
        if(!commonService.checkAccessService(subscribe.getRole(), "subscribeArticle")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        insertResponse response = new insertResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);
        try{
            var violations = validator.validate(subscribe);
            log.info("violations = {}",violations);
            var user = commonService.checkUser(subscribe.getUserId()).get();
            var article = commonService.checkArticle(subscribe.getArticleId()).get();
            if(!violations.isEmpty() || user == null || article == null)
            {
                response.setIsEror(true);
                response.setErrorCode("001");
                response.setErrorMsg("invalid request");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else if(!commonService.checkActivityType(subscribe.getActivityType()))
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

                var requetSubscribe = new FavoriteAndShareAndView();
                requetSubscribe.setArticle(article);
                requetSubscribe.setActivityType(subscribe.getActivityType());
                requetSubscribe.setMember(user);

                var newSubScribe = articleService.subscribeArticle(requetSubscribe);

                if(newSubScribe == null){
                    response.setIsEror(true);
                    response.setErrorCode("002");
                    response.setErrorMsg("insert failed");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

                response.setInsertId(newSubScribe.getId());
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

    @PostMapping("unSubscribeArticle")
    public ResponseEntity<Object> unSubscribeArticle(@RequestBody subscribeArticleRequest unSubscribe)
    {
        if(!commonService.checkAccessService(unSubscribe.getRole(), "unSubscribeArticle")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZE");
        }
        deleteResponse response = new deleteResponse();
        response.setErrorCode("200");
        response.setErrorMsg("success");
        response.setIsEror(false);
        try{
            var delteUnSubscribe = articleService.unSubscribeArticle(unSubscribe.getActivityType(),unSubscribe.getArticleId(),unSubscribe.getUserId());

            if(delteUnSubscribe == null){
                response.setIsEror(true);
                response.setErrorCode("002");
                response.setErrorMsg("delete failed");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            response.setDeleteId(delteUnSubscribe.getId());
            return ResponseEntity.ok(response);

        }catch(Throwable t){
            log.error("error occur ={}",t.getMessage());
            response.setIsEror(true);
            response.setErrorCode("500");
            response.setErrorMsg("exception or server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
