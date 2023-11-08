package com.cos.delimo.controller;

import com.cos.delimo.controller.response.community.CommunityResponse;
import com.cos.delimo.controller.response.community.NewCommentResponse;
import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.CommunityDiaryDto;
import com.cos.delimo.dto.DiaryCommentDto;
import com.cos.delimo.service.CommunityService;
import com.cos.delimo.service.DiaryService;
import com.cos.delimo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;
    private final DiaryService diaryService;
    private final MemberService memberService;


    @Autowired
    public CommunityController(CommunityService communityService, DiaryService diaryService, MemberService memberService) {
        this.communityService = communityService;
        this.diaryService = diaryService;
        this.memberService = memberService;
    }

    /**
     * 전체 공개 일기 목록과 각 댓글들 가져오기
     */

    @GetMapping("/diaries")
    ResponseEntity<Response> allDiaries() {
        CommunityResponse response = new CommunityResponse();

        List<CommunityDiaryDto> diaries = communityService.getAllDiaries();

        return response.communityGetSuccessful(diaries);
    }

    /**
     * 특정 일기 가져오기 (diary Id로)
     */
    @GetMapping("/diaries/{diaryId}")
    ResponseEntity<Response> getDiary(@RequestParam("diaryId") Long diaryId) {
        Response response = new Response();

        Optional<Diary> diary = diaryService.getDiaryById(diaryId);

        response.setMessage(ResponseMessage.DIARY_CONTENT_SUCCESSFUL);
        response.setCode(StatusCode.OK);
        response.setData(diary);

        return ResponseEntity.ok(response);
    }
    /**
     * 새로운 댓글 작성
     */
    @PostMapping("/comment/{diaryId}")
    ResponseEntity<Response> postComment(
            @RequestHeader("Authorization") String token,
            @PathVariable("diaryId") Long diaryId,
            @RequestBody DiaryCommentDto diaryCommentDto
            ) {

        NewCommentResponse newCommentResponse = new NewCommentResponse();

        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) return newCommentResponse.commentFailed();

        communityService.insertComment(diaryId, memberFind.get(), diaryCommentDto.getContent());

        return newCommentResponse.commentSuccessful();
    }
}
