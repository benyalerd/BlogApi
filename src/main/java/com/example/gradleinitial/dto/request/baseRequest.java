package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class baseRequest {
    @NotNull
    private Long role;
}
