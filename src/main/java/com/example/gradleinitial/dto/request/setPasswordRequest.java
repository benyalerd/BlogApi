package com.example.gradleinitial.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class setPasswordRequest extends baseRequest {
    @NotBlank
    private String newPassword;
    
}
