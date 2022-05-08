package com.example.gradleinitial.dto.request;

import lombok.Data;

@Data
public class getListArticleAuthor {
    private Long role;
    private Long userId;
    private Integer page;
    private Integer limit;
}
