package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class editArticleRequest {
    private Long role;
    private String articleName;
    private Long categoryId;
    private String articelShortDetail;
    private String articleLongDetail;
    private Boolean isActive;
    private Long userId;
    private Long articleId;
}
