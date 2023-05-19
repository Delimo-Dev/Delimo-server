package com.cos.security1.diary;

import com.cos.security1.domain.Diary;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.AuthenticationDto;
import com.cos.security1.dto.DiaryDto;
import com.cos.security1.dto.MemberDto;
import com.cos.security1.repository.DiaryRepository;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.security.SecurityService;
import com.cos.security1.service.DiaryService;
import com.cos.security1.service.Impl.UuidService;
import com.cos.security1.service.MemberService;
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

    @BeforeEach
    void beforeEach() {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("lyb013@gmail.com");
        memberDto.setPassword("124");

        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setContent("오늘의 일기~");
        diaryDto.setPrivacy(1); // 친구 공개

        memberService.insertUser(memberDto);
        Optional<Member> member = memberService.getUserByEmail("lyb013@gmail.com");
        diaryService.insertDiary(member.get(), diaryDto);

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
