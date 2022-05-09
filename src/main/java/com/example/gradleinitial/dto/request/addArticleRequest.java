package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class addArticleRequest extends baseRequest {
    @NotNull
    private Long userId;
    @NotBlank
    private String articleName;
    @NotNull
    private Long categoryId;
    private String articelShortDetail;
    private String articleLongDetail;

}
