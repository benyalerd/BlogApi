package com.example.gradleinitial.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Entity
@Data
@Table(name="Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CATEGORY_ID")
    private Long id;

    @Column(name="CATEGORY_NAME")
    private String categoryName;

    @Column(name="CATEGORY_IMAGE")
    private String categoryImage;

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

}
