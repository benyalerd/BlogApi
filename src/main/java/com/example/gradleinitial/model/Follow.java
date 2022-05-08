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
@Table(name="Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="FOLLOWER_ID")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member follower;

    @JoinColumn(name="FOLLOWING_ID")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member following;
    
}
