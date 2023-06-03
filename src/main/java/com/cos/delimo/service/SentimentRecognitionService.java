package com.cos.delimo.service;

import com.cos.delimo.domain.DiarySentiment;

public interface SentimentRecognitionService {
    DiarySentiment save(DiarySentiment diarySentiment);
    int getSentiment(String text);
}
