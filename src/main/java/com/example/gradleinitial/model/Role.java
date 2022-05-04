package com.example.gradleinitial.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    private String accessService;
}
