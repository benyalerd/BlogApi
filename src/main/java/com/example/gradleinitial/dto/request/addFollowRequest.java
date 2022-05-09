package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class addFollowRequest  extends baseRequest {
    @NotNull
    private  Long userId;
    @NotNull
    private Long authorId;
}
