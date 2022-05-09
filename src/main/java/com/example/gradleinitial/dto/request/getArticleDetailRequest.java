package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class getArticleDetailRequest  extends baseRequest {
    @NotNull
    private Long articleId;
    private Integer page;
    private Integer limit;
}
