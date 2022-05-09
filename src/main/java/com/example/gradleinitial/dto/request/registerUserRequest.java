package com.example.gradleinitial.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class registerUserRequest  extends baseRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String imageprofile;

}
