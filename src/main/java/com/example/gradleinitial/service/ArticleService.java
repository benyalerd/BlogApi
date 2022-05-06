package com.example.gradleinitial.service;

import com.example.gradleinitial.model.Article;
import com.example.gradleinitial.model.FavoriteAndShareAndView;
import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.repository.ArticleRepository;
import com.example.gradleinitial.repository.FavoriteAndShareAndViewRepository;
import com.example.gradleinitial.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@Slf4j
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FavoriteAndShareAndViewRepository activityRepository;

    public Article addArticle(Article article)throws Throwable{
        article.setIsActive(true);
        Article newArticle = articleRepository.save(article);
        return newArticle;
    }

    public Article deleteArticle(Long articleId){
        var article = articleRepository.findById(articleId).get();
        if(article == null) return null;

        articleRepository.delete(article);
        return article;
    }

    public Article editArticle(Long articleId,Article article)throws Throwable{
        var exitingArticle = articleRepository.findById(articleId).get();
        if(exitingArticle == null) return null;

        if(article.getArticleName()!=null)exitingArticle.setArticleName(article.getArticelShortDetail());
        if(article.getArticelShortDetail()!=null)exitingArticle.setArticelShortDetail(article.getArticelShortDetail());
        if(article.getIsActive() != exitingArticle.getIsActive())exitingArticle.setIsActive(article.getIsActive());
        if(article.getArticleLongDetail()!=null)exitingArticle.setArticleLongDetail(article.getArticleLongDetail());
        if(article.getCategory() != null)exitingArticle.setCategory(article.getCategory());

        return articleRepository.save(exitingArticle);
    }

    public FavoriteAndShareAndView subscribeArticle(FavoriteAndShareAndView request)throws Throwable{
       return activityRepository.save(request);
    }

    public FavoriteAndShareAndView unSubscribeArticle(Integer activityType,Long articleId,Long userId)throws Throwable{
        var activity = activityRepository.findByArticleIdAndMemberIdAndActivityType(articleId,userId,activityType);
        if(activity == null) return null;

        activityRepository.delete(activity);
        return activity;
    }


}
