package com.example.gradleinitial.dto.response;

import lombok.Data;

import java.util.Date;

import com.example.gradleinitial.model.Role;

@Data
public class userDetailResponse extends baseResponse{
    private Long userId;
    private String email;
    private String username;
    private Date createdDate;
    private String imageProfile;
    private Boolean isActive;
    private Role role;
    private Long followCount;

    public userDetailResponse(Long userId,String email,String username,Date createdDate,String imageProfile,Boolean isActive,Role role,Long followCount) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.createdDate = createdDate;
        this.imageProfile = imageProfile;
        this.isActive = isActive;
        this.role = role;
        this.followCount = followCount;
    }
    public userDetailResponse(){
        
    }

}
