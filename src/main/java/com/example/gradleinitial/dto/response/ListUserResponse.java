package com.example.gradleinitial.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ListUserResponse extends baseResponse {
    private List<getUserResponse> listUser;
    private Long totalRecord;
}
