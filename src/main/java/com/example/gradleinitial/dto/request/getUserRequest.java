package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class getUserRequest {
    private Integer page;
    private  Integer limit;
    private Long role;
    private Long userId;
}
