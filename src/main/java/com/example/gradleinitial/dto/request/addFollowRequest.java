package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class addFollowRequest {
    private Long role;
    private  Long userId;
    private Long authorId;
}
