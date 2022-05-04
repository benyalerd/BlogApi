package com.example.gradleinitial.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@Table(name="Article")
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ARTICLE_ID")
    private Long id;

    @JoinColumn(name="AUTHOR_ID")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member author;

     @Column(name="ARTICLE_NAME")
    private String articleName;

    @JoinColumn(name="CATEGORY_ID")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Column(name="ARTICLE_SHORT_DETAIL")
    private String articelShortDetail;

    @Column(name="ARTICLE_LONG_DETAIL")
    private String articleLongDetail;
    
    @Column(name="CREATED_DATE")
    @CreatedDate
    private Date createdDate;

    @Column(name="CREATED_BY")
    @CreatedBy
    private String createdBy;

    @Column(name="UPDATED_DATE")
    @LastModifiedDate
    private Date updatedDate;

    @Column(name="UPDATED_BY")
    @LastModifiedBy
    private String updatedBy;

    @OneToMany(mappedBy = "article")
    private List<FavoriteAndShareAndView> ratings;

    @Column(name="ISACTIVE")
    @NotNull
    private Boolean isActive;
}
