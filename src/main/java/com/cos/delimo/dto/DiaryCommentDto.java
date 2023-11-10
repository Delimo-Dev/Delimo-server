package com.cos.delimo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiaryCommentDto {
    Long memberId;
    String nickname;
    String content;
    LocalDateTime createdDate;

    @Builder
    public DiaryCommentDto(Long memberId, String nickname, String content, LocalDateTime createdDate) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.createdDate = createdDate;
    }

}
