package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class getListArticleReader extends getListArticleAuthor {
    @NotNull
    private Integer actType;
}
