package com.cos.delimo.dto;

import lombok.Data;

@Data
public class DiarySentimentUpdateDto {
    Long diaryId;
    Long sentimentId;
    int newSentiment;
}
