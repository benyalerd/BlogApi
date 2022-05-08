package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.FavoriteAndShareAndView;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FavoriteAndShareAndViewRepository extends JpaRepository<FavoriteAndShareAndView,Long> {
    public FavoriteAndShareAndView findByArticleIdAndMemberIdAndActivityType(Long articleId,Long memberId,Integer activityType);
    public Page<FavoriteAndShareAndView> findByMemberIdAndActivityType(Long memberId,Integer activityType,Pageable pageable);
}
