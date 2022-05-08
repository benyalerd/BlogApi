package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class getArticleDetailRequest {
    private Long Role;
    private Long articleId;
    private Integer page;
    private Integer limit;
}
