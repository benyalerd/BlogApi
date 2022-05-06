package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class subscribeArticleRequest {
    private Long role;
    private Long userId;
    private Long articleId;
    private Integer activityType;
}
