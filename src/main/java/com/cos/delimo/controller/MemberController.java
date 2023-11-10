package com.cos.delimo.controller;

import com.cos.delimo.controller.response.auth.*;
import com.cos.delimo.controller.response.auth.SigninResponse;
import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.*;
import com.cos.delimo.service.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@RestController
@Transactional
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;
    private final FriendRequestService friendRequestService;

    @Autowired
    MemberController(MemberService memberService, FriendRequestService friendRequestService){
        this.memberService = memberService;
        this.friendRequestService = friendRequestService;
    }

    /**
     * 로그인
     * @param user
     * @return
     */

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthenticationDto user) {
        AuthResponse response = new AuthResponse();

        // 이메일로 탐색
        Optional<Member> memberFind = memberService.getUserByEmail(user.getEmail());
        if (memberFind.isEmpty()) {
            return response.loginFailed();
        }

        // 비밀 번호 검증
        if (!memberService.verifyPassword(memberFind.get(), user.getPassword())) {
            return response.loginFailed();
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(memberFind.get().getToken());
        return response.loginSuccessful(loginResponseDto);
    }

    /**
     * 회원 가입
     * @param user
     * @return
     */
    @PostMapping("/new")
    public ResponseEntity<Response> signIn(@RequestBody MemberDto user){
        SigninResponse response = new SigninResponse();

        Optional<Member> memberFind = memberService.getUserByEmail(user.getEmail());
        if(memberFind.isEmpty()){
            Member member = memberService.insertUser(user);
            TokenDto tokenDto = new TokenDto(member.getToken());
            return response.signinSuccessful(tokenDto);
        }
        return response.emailExisted();
    }

    /**
     * Email 중복 체크
     * @param emailDto
     * @return
     */
    @GetMapping("/verifyEmail")
    public ResponseEntity<Response> verifyEmail(@RequestBody EmailDto emailDto){
        EmailVerificationResponse response = new EmailVerificationResponse();

        Optional<Member> memberFind = memberService.getUserByEmail(emailDto.getEmail());
        if (memberFind.isEmpty()) return response.emailAvailable();
        return response.emailExisted();
    }

    /**
     * 다짐 추가 및 수정
     * @param token
     * @param resolutionDto
     * @return
     */
    @PatchMapping("/updateResolution")
    public ResponseEntity<Response> updateResolution(
            @RequestHeader("Authorization") String token,
            @RequestBody ResolutionDto resolutionDto) {

        ResolutionUpdatedResponse response = new ResolutionUpdatedResponse();

        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()){
            return response.unAuthorized();
        }

        memberService.updateResolution(member.get().getId(), resolutionDto.getResolution());
        return response.resolutionUpdated(resolutionDto);
    }

    /**
     * 나의 정보 가져오기 (마이 페이지)
     * @param token
     * @return
     */
    @GetMapping("/myPage")
    public ResponseEntity<Response> getMyInfo(@RequestHeader("Authorization") String token) {
        MyPageResponse response = new MyPageResponse();

        Optional<Member> member = memberService.verifyMember(token);
        if (member.isEmpty()) {
            return response.unAuthorized();
        }

        MyPageResponseDto myPageResponseDto = MyPageResponseDto.builder()
                .id(member.get().getId())
                .code(member.get().getCode())
                .email(member.get().getEmail())
                .token(member.get().getToken())
                .nickname(member.get().getNickname())
                .resolution(member.get().getResolution())
                .friendList(memberService.getFriendList(member.get()))
                .requestedList(friendRequestService.getRequestedList(member.get()))
                .requesterList(friendRequestService.getRequesterList(member.get()))
                .diaryList(memberService.getDiaryList(member.get()))
                .build();

        return response.getMypageSuccessful(myPageResponseDto);
    }

    @GetMapping
    public List<Member> getAllUsers(){
        return memberService.getAllUsers();
    }

}
