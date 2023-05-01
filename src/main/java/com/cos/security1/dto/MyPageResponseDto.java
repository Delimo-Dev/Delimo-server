package com.cos.security1.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyPageResponseDto {
    Long id;
    String email;
    String token;
    String code;
    List<Long> friendList;
    List<Long> requestedList;
    List<Long> requesterList;

    @Builder
    MyPageResponseDto(Long id, String email, String token, String code, List<Long> friendList, List<Long> requestedList, List<Long> requesterList){
        this.id = id;
        this.email = email;
        this.token = token;
        this.code = code;
        this.friendList = friendList;
        this.requestedList = requestedList;
        this.requesterList = requesterList;

    }
}
