package com.example.gradleinitial.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name="FavoriteAndShareAndView")
public class FavoriteAndShareAndView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
    
    @JoinColumn(name="ARTICLE_ID")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @JoinColumn(name="MEMBER_ID")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name="ACTIVITY_TYPE")
    private Integer activityType;
}
