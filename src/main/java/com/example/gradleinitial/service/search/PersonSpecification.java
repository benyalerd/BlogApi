package com.example.gradleinitial.service.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.gradleinitial.dto.request.searchArticleRequest;
import com.example.gradleinitial.model.Article;

import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification implements Specification<Article>{

    private searchArticleRequest filter;

    public PersonSpecification(searchArticleRequest filter) {
        super();
        this.filter = filter;
    }
    
    public boolean CheckNullAndEmpty(String text)
    {
        return text != null && !text.isEmpty();
    }
    @Override
    public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();

        if (CheckNullAndEmpty(filter.getArticleName())) {
         p.getExpressions().add(criteriaBuilder.and(criteriaBuilder.like(root.get("articleName"), filter.getArticleName())));
        }
        if (filter.getCategoryId() != 0) {
            p.getExpressions().add(criteriaBuilder.and(criteriaBuilder.equal(root.get("category_id"), filter.getCategoryId())));
           }
         
        return p;
    }
    
}
