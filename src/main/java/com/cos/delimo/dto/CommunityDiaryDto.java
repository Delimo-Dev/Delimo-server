package com.cos.delimo.dto;

import com.cos.delimo.domain.DiaryComment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommunityDiaryDto {
    private Long diaryId;

    private Long memberId;
    private String code;
    private String nickname;

    private String content;
    private LocalDateTime createdDate;
    private List<DiaryComment> comments;


    @Builder
    CommunityDiaryDto(Long diaryId, Long memberId, String code, String nickname, String content, LocalDateTime createdDate, List<DiaryComment> comments) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.code = code;
        this.nickname = nickname;
        this.content = content;
        this.createdDate = createdDate;
        this.comments = comments;
    }

}
