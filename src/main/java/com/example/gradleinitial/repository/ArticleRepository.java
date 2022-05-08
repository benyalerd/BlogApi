package com.example.gradleinitial.repository;

import com.example.gradleinitial.dto.response.articleDetailResponse;
import com.example.gradleinitial.model.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArticleRepository  extends JpaRepository<Article,Long>,JpaSpecificationExecutor<Article> {
    String articleDetailQuerry = "select new com.example.gradleinitial.dto.response.articleDetailResponse(a.id,a.author,a.articleName,a.category,a.articelShortDetail,a.articleLongDetail,a.createdDate,a.isActive ,(select count(v) from FavoriteAndShareAndView  v where v.article.id = ?1 and v.activityType = 1 group by article.id) as likeCount,(select count(v) from FavoriteAndShareAndView  v where v.article.id = ?1 and v.activityType = 2 group by article.id) as shareCount,(select count(v) from FavoriteAndShareAndView  v where v.article.id = ?1 and v.activityType = 3 group by article.id) as viewCount) from Article  a where a.id = ?1";
    @Query(articleDetailQuerry)
    public articleDetailResponse findArticleDetailByArticleId(Long id);
    public Page<Article> findByAuthorId(Long authorId,Pageable pageable);
    public Page<Article>findAll(Specification<Article> spec,Pageable pageable);
    
}
