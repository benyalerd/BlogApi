package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.FavoriteAndShareAndView;
import com.example.gradleinitial.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FavoriteAndShareAndViewRepository extends JpaRepository<FavoriteAndShareAndView,Long> {
    public FavoriteAndShareAndView findByArticleIdAndMemberIdAndActivityType(Long articleId,Long memberId,Integer activityType);
}
