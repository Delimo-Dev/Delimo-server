package com.cos.delimo.diary;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.Member;

import com.cos.delimo.dto.DiaryDto;
import com.cos.delimo.dto.MemberDto;
import com.cos.delimo.repository.DiaryRepository;
import com.cos.delimo.repository.MemberRepository;

import com.cos.delimo.service.DiaryService;
import com.cos.delimo.service.MemberService;
import com.cos.delimo.service.SentimentRecognitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class DiaryServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private SentimentRecognitionService sentimentService;

    @BeforeEach
    void beforeEach() {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("lyb013@gmail.com");
        memberDto.setPassword("124");

        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setContent("오늘의 일기~");
        diaryDto.setPrivacy(1); // 친구 공개

        memberService.insertUser(memberDto);
        int resultSentiment = sentimentService.getSentiment(diaryDto.getContent());
        Optional<Member> member = memberService.getUserByEmail("lyb013@gmail.com");
        diaryService.insertDiary(member.get(), diaryDto, resultSentiment);

        List<Diary> diaries = diaryRepository.findAllBy();
        for(Diary diary: diaries){
            System.out.println("diary = " + diary.getMember() + diary.getContent());
        }

    }

    @Test
    void 회원의_오늘_일기_확인하기(){
        Optional<Member> member = memberService.getUserByEmail("lyb013@gmail.com");
        if(member.isPresent()) {
            Optional<Diary> todayDiary = diaryService.getTodayDiary(member.get());
            todayDiary.ifPresent(diary -> System.out.println("todayDiary = " + diary.getContent()));
        }

    }
}
