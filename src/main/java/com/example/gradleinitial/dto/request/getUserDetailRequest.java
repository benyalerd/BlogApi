package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class getUserDetailRequest {
    private Long userId;
    private Long role;
}
