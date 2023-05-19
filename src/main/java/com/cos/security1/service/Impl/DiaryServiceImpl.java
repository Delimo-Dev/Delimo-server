package com.cos.security1.service.Impl;

import com.cos.security1.domain.Diary;
import com.cos.security1.domain.DiarySentiment;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.DiaryDto;
import com.cos.security1.repository.DiaryRepository;
import com.cos.security1.repository.DiarySentimentRepository;
import com.cos.security1.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiarySentimentRepository sentimentRepository;


    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, DiarySentimentRepository sentimentRepository) {
        this.diaryRepository = diaryRepository;
        this.sentimentRepository = sentimentRepository;
    }

    /**
     * 오늘의 일기 작성하기
     *
     * @param member
     * @param diaryDto
     */
    @Override
    public void insertDiary(Member member, DiaryDto diaryDto, int sentiment) {
        Diary todayDiary = Diary.builder()
                .content(diaryDto.getContent())
                .member(member)
                .privacy(diaryDto.getPrivacy())
                .build();

        DiarySentiment diarySentiment = DiarySentiment.builder()
                .sentiment(sentiment)
                .diary(todayDiary)
                .build();

        sentimentRepository.save(diarySentiment);
        todayDiary.updateSentiment(diarySentiment);

        diaryRepository.save(todayDiary);
    }

    /**
     * 오늘 날짜에 해당하는 일기 가져오기 (일기 작성되어 있는 경우)
     * @param member
     * @return
     */
    @Override
    public Optional<Diary> getTodayDiary(Member member) {
        List<Diary> diaries = member.getDiaryList();
        if (diaries != null) {
            LocalDate today = LocalDate.now();
            return diaries.stream()
                    .filter(diary -> diary.getCreatedDate().toLocalDate().equals(today))
                    .findFirst();
        }
        return Optional.empty();
    }

    /**
     * 기존 일기 수정하기
     * @param member
     * @param diaryDto
     */
    @Override
    public void updateDiary(Member member, DiaryDto diaryDto, int sentiment) {
        Optional<Diary> todayDiary = getTodayDiary(member);
        sentimentRepository.updateSentiment(todayDiary.get().getDiarySentiment().getId(), sentiment);
        diaryRepository.updateDiary(todayDiary.get().getId(), diaryDto.getContent(), diaryDto.getPrivacy(), todayDiary.get().getDiarySentiment());
    }
}
