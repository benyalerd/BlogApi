package com.example.gradleinitial.dto.response;

import java.util.Date;

import com.example.gradleinitial.model.Category;
import com.example.gradleinitial.model.Member;

import lombok.Data;

@Data
public class getArticleResponse {
    private Long id;
    private Member author;
    private String articleName;
    private Category category;
    private String articelShortDetail;
    private String articleLongDetail;
    private Date createdDate;
    private Boolean isActive;
}
