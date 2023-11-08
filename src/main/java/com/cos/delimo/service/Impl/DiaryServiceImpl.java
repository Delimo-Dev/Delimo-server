package com.cos.delimo.service.Impl;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.DiarySentiment;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.DiaryDto;
import com.cos.delimo.dto.DiarySentimentUpdateDto;
import com.cos.delimo.repository.DiaryRepository;
import com.cos.delimo.repository.DiarySentimentRepository;
import com.cos.delimo.service.DiaryService;
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
    public Diary insertDiary(Member member, DiaryDto diaryDto, int sentiment) {
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

        return diaryRepository.save(todayDiary);
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
     * 일기 id 값으로 일기 가져오기
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Diary> getDiaryById(Long id) {
        return diaryRepository.findById(id);
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

    /**
     * Diary 조회수 증가시키기
     * @param diary
     */
    @Override
    public void updateVisited(Diary diary) {
        diaryRepository.updateVisited(diary.getId());
    }

    /**
     * 일기의 감정 분석 결과 변경하기
     * @param sentimentUpdateDto
     */
    @Override
    public void updateSentiment(DiarySentimentUpdateDto sentimentUpdateDto) {
        sentimentRepository.updateSentiment(sentimentUpdateDto.getSentimentId(), sentimentUpdateDto.getNewSentiment());
    }
}
