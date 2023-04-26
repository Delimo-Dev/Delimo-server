package com.cos.security1.controller;

import com.cos.security1.controller.response.FriendRequestedResponse;
import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.FriendDto;
import com.cos.security1.service.FriendRequestService;
import com.cos.security1.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/friend")
public class FriendController {
    private final MemberService memberService;
    private final FriendRequestService friendRequestService;

    @Autowired
    FriendController(MemberService memberService, FriendRequestService friendRequestService){
        this.memberService = memberService;
        this.friendRequestService = friendRequestService;
    }


    Optional<Member> findMember(String token){
        String bearerToken = token.substring(7);
        return memberService.verifyMember(bearerToken);
    }

    // 친구 검색 (코드로 검색 하기)

    // 친구 신청 하기 (Requester -> Requested)
    @PostMapping("/request")
    ResponseEntity<FriendRequestedResponse> requestFriend(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto){

        FriendRequestedResponse response = new FriendRequestedResponse();

        // 인증 실패
        Optional<Member> memberFind = findMember(token);
        if(memberFind.isEmpty()){
            response.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if(friend.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        friendRequestService.requestFriend(memberFind.get(), friend.get());

        response.builder()
                .code(StatusCode.CREATED)
                .message(ResponseMessage.FRIEND_REQUESTED_SUCCESS);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    // 친구 신청에 대해 승인 (FriendRequest 삭제와 동시에 friendlist에 추가)


    // 친구 신청에 대한 삭제 (FriendRequest 삭제)

    // 현재 친구들 목록 보기


}
