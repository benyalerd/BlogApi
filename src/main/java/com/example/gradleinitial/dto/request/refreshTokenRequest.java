package com.example.gradleinitial.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class refreshTokenRequest  extends baseRequest {
    
    @NotBlank
    private String token;
}
