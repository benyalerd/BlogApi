package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class getListArticleAuthor  extends baseRequest {
    @NotNull
    private Long userId;
    private Integer page;
    private Integer limit;
}
