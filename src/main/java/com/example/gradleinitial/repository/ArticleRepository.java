package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.Article;
import com.example.gradleinitial.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArticleRepository  extends JpaRepository<Article,Long> {
}
