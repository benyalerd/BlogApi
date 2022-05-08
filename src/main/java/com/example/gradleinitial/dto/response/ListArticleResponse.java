package com.example.gradleinitial.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class ListArticleResponse extends baseResponse {
    private List<getArticleResponse> listArticle;
    private Long totalRecord;
}
