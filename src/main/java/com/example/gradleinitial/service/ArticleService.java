package com.example.gradleinitial.service;

import com.example.gradleinitial.dto.request.searchArticleRequest;
import com.example.gradleinitial.model.Article;
import com.example.gradleinitial.model.FavoriteAndShareAndView;
import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.repository.ArticleRepository;
import com.example.gradleinitial.repository.FavoriteAndShareAndViewRepository;
import com.example.gradleinitial.repository.UserTokenRepository;
import com.example.gradleinitial.service.search.PersonSpecification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.transaction.Transactional;

@Component
@Transactional
@Slf4j
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FavoriteAndShareAndViewRepository favoriteAndShareAndViewRepository;

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

    public Page<Article> getlistArticleAuthor(Long userId,Integer page,Integer limit)throws Throwable{
       return articleRepository.findByAuthorId(userId,PageRequest.of(page, limit));     
    }

    public Page<FavoriteAndShareAndView> getlistArticleReader(Integer actType,Long userId,Integer page,Integer limit)throws Throwable{
        return favoriteAndShareAndViewRepository.findByMemberIdAndActivityType(userId,actType,PageRequest.of(page, limit));     
     }

     public Page<Article> searchArticle(searchArticleRequest request)throws Throwable{
        Specification<Article> spec = new PersonSpecification(request);
        Page<Article> listArticle =articleRepository.findAll(spec,PageRequest.of(request.getPage(),request.getLimit()));
        return listArticle;
     }


}
