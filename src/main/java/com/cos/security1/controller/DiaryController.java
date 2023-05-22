package com.cos.security1.controller;

import com.cos.security1.controller.response.diary.DiaryContentResponse;
import com.cos.security1.controller.response.diary.DiaryCreatedResponse;
import com.cos.security1.controller.response.diary.SentimentUpdatedResponse;
import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import com.cos.security1.domain.Diary;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.DiaryDto;
import com.cos.security1.dto.DiaryResponseDto;
import com.cos.security1.dto.DiarySentimentUpdateDto;
import com.cos.security1.dto.DiarySentimentUpdateResponseDto;
import com.cos.security1.service.DiaryService;
import com.cos.security1.service.MemberService;
import com.cos.security1.service.SentimentRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<DiaryCreatedResponse> newDiary(
            @RequestHeader("Authorization") String token,
            @RequestBody DiaryDto diaryDto) {

        DiaryCreatedResponse response = new DiaryCreatedResponse();

        // 회원 검증
        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()) {
            response = DiaryCreatedResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        if (diaryDto.getContent().length() == 0) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 일기 작성 및 수정
        Optional<Diary> todayDiary = diaryService.getTodayDiary(member.get());
        int resultSentiment = sentimentService.getSentiment(diaryDto.getContent());

        Diary diaryGet = null;
        boolean created = false;
        if (todayDiary.isEmpty()) {
            diaryGet = diaryService.insertDiary(member.get(), diaryDto, resultSentiment);
            created = true;
        }
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

        response = DiaryCreatedResponse.builder()
                .code(StatusCode.CREATED)
                .message(ResponseMessage.DIARY_CREATED)
                .data(diaryData)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    /**
     * 오늘의 일기 조회하기
     * @param token
     * @return
     */

    @GetMapping("/today")
    ResponseEntity<DiaryContentResponse> getTodayDiary(@RequestHeader("Authorization") String token) {
        DiaryContentResponse response = new DiaryContentResponse();

        // 회원 검증
        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()) {
            response = DiaryContentResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 오늘의 일기 가져 오기
        Optional<Diary> todayDiary = diaryService.getTodayDiary(member.get());
        if (todayDiary.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

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

        response = DiaryContentResponse.builder()
                .code(StatusCode.OK)
                .message(ResponseMessage.DIARY_CONTENT_SUCCESSFUL)
                .data(diaryData)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 감정 결과 수정하기
     * @param sentimentUpdateDto
     * @return
     */
    @PatchMapping("/updateSentiment")
    ResponseEntity<SentimentUpdatedResponse> updateSentiment(@RequestBody DiarySentimentUpdateDto sentimentUpdateDto){
        SentimentUpdatedResponse response = new SentimentUpdatedResponse();

        diaryService.updateSentiment(sentimentUpdateDto);
        DiarySentimentUpdateResponseDto updatedData = DiarySentimentUpdateResponseDto.builder()
                .diaryId(sentimentUpdateDto.getDiaryId())
                .sentimentId(sentimentUpdateDto.getSentimentId())
                .updatedSentiment(sentimentUpdateDto.getNewSentiment())
                .build();

        response.setData(updatedData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
