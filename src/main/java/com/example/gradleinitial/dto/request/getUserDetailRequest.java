package com.example.gradleinitial.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class getUserDetailRequest  extends baseRequest{
    @NotNull
    private Long userId;
    
}
