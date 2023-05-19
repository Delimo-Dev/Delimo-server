package com.cos.security1.service;

import com.cos.security1.domain.DiarySentiment;

public interface SentimentRecognitionService {
    DiarySentiment save(DiarySentiment diarySentiment);
    int getSentiment(String text);
}
