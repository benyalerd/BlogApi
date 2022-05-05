package com.example.gradleinitial.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class refreshTokenRequest {
    @NotNull
    private Long role;
    @NotBlank
    private String token;
}
