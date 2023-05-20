package com.cos.security1.dto;

import lombok.Data;

@Data
public class DiarySentimentUpdateDto {
    Long diaryId;
    Long sentimentId;
    int newSentiment;
}
