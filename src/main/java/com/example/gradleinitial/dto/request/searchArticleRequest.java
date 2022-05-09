package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class searchArticleRequest  extends baseRequest {
    private Long categoryId;
    private String articleName;
    private Integer page;
    private Integer limit;
}
