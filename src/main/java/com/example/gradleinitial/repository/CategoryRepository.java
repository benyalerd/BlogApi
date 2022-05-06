package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.Article;
import com.example.gradleinitial.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
