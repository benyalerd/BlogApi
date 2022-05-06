package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class editUserRequest {
    private String imageprofile;
    private String username;
    private Long role;
}
