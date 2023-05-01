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
    String nickname;
    String resolution;
    List<Long> friendList;
    List<Long> requestedList;
    List<Long> requesterList;

    @Builder
    MyPageResponseDto(Long id, String email, String nickname, String token, String code, String resolution, List<Long> friendList, List<Long> requestedList, List<Long> requesterList){
        this.id = id;
        this.email = email;
        this.token = token;
        this.nickname = nickname;
        this.code = code;
        this.resolution = resolution;
        this.friendList = friendList;
        this.requestedList = requestedList;
        this.requesterList = requesterList;

    }
}