package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class addArticleRequest {
    private Long role;
    private Long userId;
    private String articleName;
    private Long categoryId;
    private String articelShortDetail;
    private String articleLongDetail;

}
