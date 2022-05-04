package com.example.gradleinitial.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="UserToken")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date startDate;

    private Date expireDate;

    private Boolean status;

    private Date refreshDateTime;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client refClient;
}
