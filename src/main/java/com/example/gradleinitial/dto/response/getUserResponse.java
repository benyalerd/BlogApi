package com.example.gradleinitial.dto.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.gradleinitial.model.Role;

import java.util.Date;

@Data
public class getUserResponse extends baseResponse {

    private String username;
    private String email;
    private String password;
    private Role role;
    private String imageprofile;
    private Date createdDate;
    private Boolean isActive;
}
