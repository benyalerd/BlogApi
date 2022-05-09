package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class editArticleRequest  extends baseRequest {
    private String articleName;
    private Long categoryId;
    private String articelShortDetail;
    private String articleLongDetail;
    private Boolean isActive;
    @NotNull
    private Long userId;
    @NotNull
    private Long articleId;
}
