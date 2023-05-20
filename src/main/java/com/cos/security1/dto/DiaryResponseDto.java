package com.cos.security1.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponseDto {
    private Long diaryId;
    private Long sentimentId;
    private String content;
    private int privacy;
    private int sentiment;
    private int visited;

    @Builder
    DiaryResponseDto(Long diaryId, Long sentimentId, String content, int privacy, int sentiment, int visited){
        this.diaryId = diaryId;
        this.sentimentId = sentimentId;
        this.content = content;
        this.privacy = privacy;
        this.sentiment = sentiment;
        this.visited = visited;
    }
}
