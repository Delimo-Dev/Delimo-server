package com.cos.security1.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DiarySentimentUpdateResponseDto {
    private Long diaryId;
    private Long sentimentId;
    private int updatedSentiment;

    @Builder
    DiarySentimentUpdateResponseDto(Long diaryId, Long sentimentId, int updatedSentiment){
        this.diaryId = diaryId;
        this.sentimentId = sentimentId;
        this.updatedSentiment = updatedSentiment;
    }
}
