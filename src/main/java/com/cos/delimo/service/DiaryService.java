package com.cos.delimo.service;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.DiaryDto;
import com.cos.delimo.dto.DiaryResponseDto;
import com.cos.delimo.dto.DiarySentimentUpdateDto;

import java.util.List;
import java.util.Optional;

public interface DiaryService {
    Diary insertDiary(Member member, DiaryDto diaryDto, int sentiment);
    Optional<Diary> getTodayDiary(Member member);
    Optional<DiaryResponseDto> getDiaryById(Long id);
    void updateDiary(Member member, DiaryDto diaryDto, int sentiment);
    void updateVisited(Diary diary);
    void updateSentiment(DiarySentimentUpdateDto sentimentUpdateDto);
    List<DiaryResponseDto> getAllDiaries(Member member);
}
