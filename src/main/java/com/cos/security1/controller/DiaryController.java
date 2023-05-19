package com.cos.security1.controller;

import com.cos.security1.controller.response.diary.DiaryContentResponse;
import com.cos.security1.controller.response.diary.DiaryCreatedResponse;
import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import com.cos.security1.domain.Diary;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.DiaryDto;
import com.cos.security1.service.DiaryService;
import com.cos.security1.service.MemberService;
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

    @Autowired
    public DiaryController(DiaryService diaryService, MemberService memberService) {
        this.diaryService = diaryService;
        this.memberService = memberService;
    }

    /**
     * 오늘의 일기 작성하기
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

        // 오늘의 일기 가져 오기
        Optional<Diary> todayDiary = diaryService.getTodayDiary(member.get());

        // 새로 작성
        if (todayDiary.isEmpty()){
            diaryService.insertDiary(member.get(), diaryDto);
        }
        // 수정
        else {
            diaryService.updateDiary(member.get(), diaryDto);
        }

        response = DiaryCreatedResponse.builder()
                .code(StatusCode.CREATED)
                .message(ResponseMessage.DIARY_CREATED)
                .data(diaryDto)
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

        response = DiaryContentResponse.builder()
                .code(StatusCode.OK)
                .message(ResponseMessage.DIARY_CONTENT_SUCCESSFUL)
                .data(todayDiary.get().getContent())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
