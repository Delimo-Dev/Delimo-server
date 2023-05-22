package com.cos.security1.service;

import com.cos.security1.domain.Diary;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.DiaryDto;
import com.cos.security1.dto.DiarySentimentUpdateDto;

import java.util.Optional;

public interface DiaryService {
    Diary insertDiary(Member member, DiaryDto diaryDto, int sentiment);
    Optional<Diary> getTodayDiary(Member member);
    void updateDiary(Member member, DiaryDto diaryDto, int sentiment);
    void updateVisited(Diary diary);
    void updateSentiment(DiarySentimentUpdateDto sentimentUpdateDto);
}
