package com.example.gradleinitial.dto.response;

import java.util.Date;

import com.example.gradleinitial.model.Category;
import com.example.gradleinitial.model.Member;

import lombok.Data;

@Data
public class articleDetailResponse {
    private Long id;
    private Member author;
    private String articleName;
    private Category category;
    private String articelShortDetail;
    private String articleLongDetail;
    private Date createdDate;
    private Boolean isActive;
    private Long favCount;
    private Long viewCount;
    private Long shareCount;

    public articleDetailResponse(Long id,Member author,String articleName,Category category,String articelShortDetail,String articelLongDetail,Date createdDate,Boolean isActive,Long favCount,Long shareCount,Long viewCount) {
        this.id = id;
        this.author = author;
        this.articleName = articleName;
        this.category = category;
        this.articelShortDetail = articelShortDetail;
        this.articleLongDetail = articelLongDetail;
        this.createdDate = createdDate;
        this.isActive = isActive;
        this.favCount = favCount;
        this.viewCount = viewCount;
        this.shareCount = shareCount;
    }
    public articleDetailResponse(){
        
    }
}
