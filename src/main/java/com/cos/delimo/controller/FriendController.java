package com.cos.delimo.controller;

import com.cos.delimo.controller.response.friend.*;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.domain.*;
import com.cos.delimo.dto.*;
import com.cos.delimo.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RestController
@Transactional
@RequestMapping("/friend")
public class FriendController {
    private final MemberService memberService;
    private final FriendRequestService friendRequestService;

    @Autowired
    FriendController(MemberService memberService, FriendRequestService friendRequestService) {
        this.memberService = memberService;
        this.friendRequestService = friendRequestService;
    }

    /**
     * 친구 신청 보내기 (requester -> requested)
     *
     * @param token
     * @param friendDto
     * @return
     */
    @PostMapping("/request")
    ResponseEntity<FriendRequestedResponse> requestFriend(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto) {

        FriendRequestedResponse response = new FriendRequestedResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) {
            response = FriendRequestedResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if (friend.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // 자기 자신인 경우
        if (friend.get().getId().equals(memberFind.get().getId())) {
            response = FriendRequestedResponse.builder()
                            .message(ResponseMessage.REQUEST_FAILED)
                            .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        System.out.println(memberFind.get().getFriendList());
        System.out.println(friend.get().getFriendList());

        // 이미 친구인 경우
        for (FriendList friendList:memberFind.get().getFriendList()) {
            if (friendList.getFriendId().equals(friend.get().getId())) {
                response = FriendRequestedResponse.builder()
                        .message(ResponseMessage.FRIEND_INCLUDED)
                        .build();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        FriendRequest findRequest = friendRequestService.requestFriend(memberFind.get(), friend.get());
        // 이미 친구로 신청한 경우
        if (findRequest == null) {
            response = FriendRequestedResponse.builder()
                            .message(ResponseMessage.REQUEST_EXISTED)
                            .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = FriendRequestedResponse.builder()
                    .code(StatusCode.CREATED)
                    .message(ResponseMessage.FRIEND_REQUESTED_SUCCESS)
                    .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 친구 코드로 친구 검색하기
     *
     * @param code
     * @return
     */
    @PostMapping("/findByCode")
    ResponseEntity<FriendFoundResponse> findByCode(@RequestBody CodeDto code) {
        FriendFoundResponse response = new FriendFoundResponse();

        Optional<Member> findMember = friendRequestService.findByCode(code.getCode());
        if (findMember.isPresent()) {
            FriendInfoDto friendInfoDto = new FriendInfoDto();

            friendInfoDto.setFriendId(findMember.get().getId());
            friendInfoDto.setNickname(findMember.get().getNickname());
            friendInfoDto.setResolution(findMember.get().getResolution());

            response = FriendFoundResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.FRIEND_FOUND)
                    .data(friendInfoDto)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    /**
     * 친구 신청을 승인합니다.
     * @param token
     * @param friendDto
     * @return
     */
    @PostMapping("/acceptRequest")
    ResponseEntity<AcceptFriendResponse> acceptFriendRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto) {
        AcceptFriendResponse response = new AcceptFriendResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) {
            response = AcceptFriendResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if (friend.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<FriendRequest> friendRequest = friendRequestService.findFriendRequest(friend.get(), memberFind.get());
        if (friendRequest.size() == 0) {
            response = AcceptFriendResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.FRIEND_REQUEST_ACCEPTED_FAILED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        friendRequestService.acceptFriend(friendRequest.get(0));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /**
     * 친구 신청을 거절합니다.
     * @param token
     * @param friendDto
     * @return
     */
    @PostMapping("/rejectRequest")
    ResponseEntity<RejectFriendResponse> rejectFriendRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto) {
        RejectFriendResponse response = new RejectFriendResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) {
            response = RejectFriendResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if (friend.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<FriendRequest> friendRequest = friendRequestService.findFriendRequest(friend.get(), memberFind.get());
        if (friendRequest.size() == 0) {
            response = RejectFriendResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.FRIEND_REQUEST_ACCEPTED_FAILED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        friendRequestService.rejectFriend(friendRequest.get(0));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /**
     * 친구 목록을 조회합니다.
     * @param token
     * @return
     */
    @GetMapping("/list")
    ResponseEntity<FriendListResponse> getFriends(@RequestHeader("Authorization") String token){
        FriendListResponse response = new FriendListResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) {
            response = FriendListResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        List<FriendList> friendList = memberFind.get().getFriendList();
        List<FriendInfoDto> friendInfos = new ArrayList<>();
        for(FriendList friend: friendList){
            Optional<Member> member = memberService.getUserById(friend.getFriendId());
            if (member.isEmpty()){
                continue;
            }
            friendInfos.add(new FriendInfoDto(member.get().getId(), member.get().getNickname(), member.get().getResolution()));
        }

        response.setData(friendInfos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 받은 친구 신청 목록을 조회합니다.
     * @param token
     * @return
     */
    @GetMapping("/requested")
    ResponseEntity<RequestedListResponse> getRequestedList(@RequestHeader("Authorization") String token){
        RequestedListResponse response = new RequestedListResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) {
            response = RequestedListResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        List<FriendRequest> requestedList = memberFind.get().getRequestedList();
        List<FriendInfoDto> friendInfos = new ArrayList<>();
        for(FriendRequest friendRequest:requestedList){
            Member friend = friendRequest.getRequester();
            friendInfos.add(new FriendInfoDto(friend.getId(), friend.getNickname(), friend.getResolution()));
        }

        response.setData(friendInfos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
