package com.example.gradleinitial.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="Member")
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    @NotNull
    private String username;
    
    @Column(name="EMAIL")
    @NotNull
    private String email;

    @Column(name="PASSWORD")
    @NotNull
    private String password;

    @Column(name="SALT")
    @NotNull
    private String salt;

    @Column(name="CREATED_DATE")
    @CreatedDate
    @Temporal(TemporalType.DATE)
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

    @JoinColumn(name="ROLE")
    @OneToOne
    private Role role;

    @Column(name="ISACTIVE")
    @NotNull
    private Boolean isActive;

    @Column(name = "IMAGE_PROFILE")
    private String imageprofile;

}
