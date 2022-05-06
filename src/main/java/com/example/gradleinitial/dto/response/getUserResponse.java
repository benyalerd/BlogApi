package com.example.gradleinitial.dto.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class getUserResponse extends baseResponse {

    private String username;
    private String email;
    private String password;
    private Long role;
    private String imageprofile;
    private Date createdDate;
    private Boolean isActive;
}
