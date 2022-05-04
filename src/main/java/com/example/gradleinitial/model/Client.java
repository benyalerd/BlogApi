package com.example.gradleinitial.model;
import lombok.Data;

import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name="Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    private Long consentLifetime;

    private String description;
}