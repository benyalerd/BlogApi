package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class editUserRequest  extends baseRequest {
    private String imageprofile;
    private String username;   
}
