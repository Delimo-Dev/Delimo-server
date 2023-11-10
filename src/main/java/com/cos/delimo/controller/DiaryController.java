package com.cos.delimo.controller;

import com.cos.delimo.controller.response.diary.DiaryContentResponse;
import com.cos.delimo.controller.response.diary.DiaryCreatedResponse;
import com.cos.delimo.controller.response.diary.DiaryListResponse;
import com.cos.delimo.controller.response.diary.SentimentUpdatedResponse;
import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.DiaryDto;
import com.cos.delimo.dto.DiaryResponseDto;
import com.cos.delimo.dto.DiarySentimentUpdateDto;
import com.cos.delimo.dto.DiarySentimentUpdateResponseDto;
import com.cos.delimo.service.DiaryService;
import com.cos.delimo.service.MemberService;
import com.cos.delimo.service.SentimentRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final MemberService memberService;
    private final SentimentRecognitionService sentimentService;

    @Autowired
    public DiaryController(DiaryService diaryService, MemberService memberService, SentimentRecognitionService sentimentService) {
        this.diaryService = diaryService;
        this.memberService = memberService;
        this.sentimentService = sentimentService;
    }

    /**
     * 오늘의 일기 작성 후 감정 분석 결과 도출
     *
     * @param token
     * @param diaryDto
     * @return
     */
    @PostMapping("/today")
    ResponseEntity<Response> newDiary(
            @RequestHeader("Authorization") String token,
            @RequestBody DiaryDto diaryDto) {

        DiaryCreatedResponse response = new DiaryCreatedResponse();

        // 회원 검증
        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()) return response.unAuthorized();

        // NO_CONTENT
        if (diaryDto.getContent().length() == 0) return response.diaryNoContent();

        // 일기 작성 및 수정
        Optional<Diary> todayDiary = diaryService.getTodayDiary(member.get());
        int resultSentiment = sentimentService.getSentiment(diaryDto.getContent());

        Diary diaryGet = null;
        boolean created = false;
        // 오늘 새로운 일기 작성
        if (todayDiary.isEmpty()) {
            diaryGet = diaryService.insertDiary(member.get(), diaryDto, resultSentiment);
            created = true;
        }
        // 기존 일기 수정
        else {
            diaryService.updateDiary(member.get(), diaryDto, resultSentiment);
        }

        if (!created){
            diaryGet = todayDiary.get();
        }

        DiaryResponseDto diaryData = DiaryResponseDto.builder()
                .diaryId(diaryGet.getId())
                .sentimentId(diaryGet.getDiarySentiment().getId())
                .content(diaryDto.getContent())
                .privacy(diaryDto.getPrivacy())
                .sentiment(resultSentiment)
            .build();
        return response.diaryCreated(diaryData);
    }


    /**
     * 오늘의 일기 조회하기
     * @param token
     * @return
     */

    @GetMapping("/today")
    ResponseEntity<Response> getTodayDiary(@RequestHeader("Authorization") String token) {
        DiaryContentResponse response = new DiaryContentResponse();

        // 회원 검증
        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()) response.unAuthorized();

        // 오늘의 일기 가져 오기
        Optional<Diary> todayDiary = diaryService.getTodayDiary(member.get());
        if (todayDiary.isEmpty()) return response.diaryNotCreated();

        // 조회수 증가
        diaryService.updateVisited(todayDiary.get());
        todayDiary.get().updateVisited();

        DiaryResponseDto diaryData = DiaryResponseDto.builder()
                .diaryId(todayDiary.get().getId())
                .sentimentId(todayDiary.get().getDiarySentiment().getId())
                .content(todayDiary.get().getContent())
                .privacy(todayDiary.get().getPrivacy())
                .sentiment(todayDiary.get().getDiarySentiment().getSentiment())
                .visited(todayDiary.get().getVisited())
                .build();

       return response.diaryGetSuccessful(diaryData);
    }

    /**
     * 감정 결과 수정하기
     * @param sentimentUpdateDto
     * @return
     */
    @PatchMapping("/updateSentiment")
    ResponseEntity<Response> updateSentiment(@RequestBody DiarySentimentUpdateDto sentimentUpdateDto){
        SentimentUpdatedResponse response = new SentimentUpdatedResponse();

        diaryService.updateSentiment(sentimentUpdateDto);
        DiarySentimentUpdateResponseDto updatedData = DiarySentimentUpdateResponseDto.builder()
                .diaryId(sentimentUpdateDto.getDiaryId())
                .sentimentId(sentimentUpdateDto.getSentimentId())
                .updatedSentiment(sentimentUpdateDto.getNewSentiment())
                .build();
        return response.sentimentUpdated(updatedData);
    }

    /**
     * 내가 작성한 일기 목록 보기
     */
    @GetMapping("/list")
    ResponseEntity<Response> diaryLists(
            @RequestHeader("Authorization") String token
    ) {
        DiaryListResponse response = new DiaryListResponse();
        // 회원 검증
        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()) response.unAuthorized();

        List<DiaryResponseDto> diaries = diaryService.getAllDiaries(member.get());
        return response.diaryListSuccessful(diaries);

    }
}
