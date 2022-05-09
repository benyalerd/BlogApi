package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class getListUserRequest  extends baseRequest {
    private Integer page;
    private  Integer limit;
    @NotNull
    private Long userId;
}
