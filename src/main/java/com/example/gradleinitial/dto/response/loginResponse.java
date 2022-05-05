package com.example.gradleinitial.dto.response;

import lombok.Data;

@Data
public class loginResponse extends baseResponse {
    private String token;
}
