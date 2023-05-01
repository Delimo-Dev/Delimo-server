package com.cos.security1.controller;

import com.cos.security1.controller.response.friend.AcceptFriendResponse;
import com.cos.security1.controller.response.friend.FriendFoundResponse;
import com.cos.security1.controller.response.friend.FriendRequestedResponse;
import com.cos.security1.controller.response.friend.RejectFriendResponse;
import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.FriendDto;
import com.cos.security1.service.FriendRequestService;
import com.cos.security1.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 친구 신청 보내기 (requester -> requested)
     * @param token
     * @param friendDto
     * @return
     */
    @PostMapping("/request")
    ResponseEntity<FriendRequestedResponse> requestFriend(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto){

        FriendRequestedResponse response = new FriendRequestedResponse();

        // 인증 실패
        Optional<Member> memberFind = findMember(token);
        if(memberFind.isEmpty()){
            response = FriendRequestedResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if(friend.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // 자기 자신인 경우
        if (friend.get().getId().equals(memberFind.get().getId())){
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMessage(ResponseMessage.REQUEST_FAILED);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        FriendRequest findRequest = friendRequestService.requestFriend(memberFind.get(), friend.get());
        // 이미 친구로 신청한 경우
        if (findRequest == null){
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMessage(ResponseMessage.REQUEST_EXISTED);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = FriendRequestedResponse.builder()
                .code(StatusCode.CREATED)
                .message(ResponseMessage.FRIEND_REQUESTED_SUCCESS)
                .build();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    /**
     * 친구 코드로 친구 검색하기
     * @param code
     * @return
     */
    @GetMapping("/findByCode")
    ResponseEntity<FriendFoundResponse> findByCode(@RequestParam String code){
        FriendFoundResponse response = new FriendFoundResponse();

        Optional<Member> findMember = friendRequestService.findByCode(code);
        if(findMember.isPresent()){
            response = FriendFoundResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.FRIEND_FOUND)
                    .data(findMember.get().getId())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // 친구 신청에 대해 승인 (FriendRequest 삭제와 동시에 friendlist에 추가)
    @PostMapping("/acceptRequest")
    ResponseEntity<AcceptFriendResponse> acceptFriendRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto)
    {
        AcceptFriendResponse response = new AcceptFriendResponse();

        // 인증 실패
        Optional<Member> memberFind = findMember(token);
        if(memberFind.isEmpty()){
            response = AcceptFriendResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if(friend.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<FriendRequest> friendRequest = friendRequestService.findFriendRequest(friend.get(), memberFind.get());
        if(friendRequest.size() == 0){
            response = AcceptFriendResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.FRIEND_REQUEST_ACCEPTED_FAILED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        friendRequestService.acceptFriend(friendRequest.get(0));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    // 친구 신청에 대한 삭제 (FriendRequest 삭제)
    @PostMapping("/rejectRequest")
    ResponseEntity<RejectFriendResponse> rejectFriendRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto)
    {
        RejectFriendResponse response = new RejectFriendResponse();

        // 인증 실패
        Optional<Member> memberFind = findMember(token);
        if(memberFind.isEmpty()){
            response = RejectFriendResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if(friend.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<FriendRequest> friendRequest = friendRequestService.findFriendRequest(friend.get(), memberFind.get());
        if(friendRequest.size() == 0){
            response = RejectFriendResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.FRIEND_REQUEST_ACCEPTED_FAILED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        friendRequestService.rejectFriend(friendRequest.get(0));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    // 현재 친구들 목록 보기

}
