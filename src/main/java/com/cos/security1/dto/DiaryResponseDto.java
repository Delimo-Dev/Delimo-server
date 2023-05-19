package com.cos.security1.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponseDto {
    private String content;
    private int privacy;
    private int sentiment;

    @Builder
    DiaryResponseDto(String content, int privacy, int sentiment){
        this.content = content;
        this.privacy = privacy;
        this.sentiment = sentiment;
    }
}
