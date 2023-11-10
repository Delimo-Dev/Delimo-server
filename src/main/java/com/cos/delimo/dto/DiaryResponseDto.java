package com.cos.delimo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public
class DiaryResponseDto {
    private Long diaryId;
    private Long sentimentId;
    private String content;
    private int privacy;
    private int sentiment;
    private int visited;
    private LocalDateTime createdDate;

    @Builder
    DiaryResponseDto(Long diaryId, Long sentimentId, String content, int privacy, int sentiment, int visited, LocalDateTime createdDate){
        this.diaryId = diaryId;
        this.sentimentId = sentimentId;
        this.content = content;
        this.privacy = privacy;
        this.sentiment = sentiment;
        this.visited = visited;
        this.createdDate = createdDate;
    }
}
