package com.cos.security1.service;

import com.cos.security1.domain.Diary;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.DiaryDto;

import java.util.Optional;

public interface DiaryService {
    void insertDiary(Member member, DiaryDto diaryDto, int sentiment);
    Optional<Diary> getTodayDiary(Member member);
    void updateDiary(Member member, DiaryDto diaryDto, int sentiment);
}
