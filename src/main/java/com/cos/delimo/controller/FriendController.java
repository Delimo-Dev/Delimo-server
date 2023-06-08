package com.cos.delimo.controller;

import com.cos.delimo.controller.response.friend.*;
import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.domain.*;
import com.cos.delimo.dto.*;
import com.cos.delimo.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    ResponseEntity<Response> requestFriend(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto) {

        FriendRequestedResponse response = new FriendRequestedResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) return response.unAuthorized();

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if (friend.isEmpty()) return response.friendNotFound();

        // 자기 자신인 경우
        if (friend.get().getId().equals(memberFind.get().getId())) return response.requestFailed();

        // 이미 친구인 경우
        for (FriendList friendList:memberFind.get().getFriendList()) {
            if (friendList.getFriendId().equals(friend.get().getId())) return response.alreadyFriend();
        }

        // 이미 친구로 신청한 경우
        FriendRequest findRequest = friendRequestService.requestFriend(memberFind.get(), friend.get());
        if (findRequest == null) return response.requestExisted();

        return response.requestSuccessful();
    }

    /**
     * 친구 코드로 친구 검색하기
     *
     * @param code
     * @return
     */
    @PostMapping("/findByCode")
    ResponseEntity<Response> findByCode(@RequestBody CodeDto code) {
        FriendFoundResponse response = new FriendFoundResponse();

        Optional<Member> findMember = friendRequestService.findByCode(code.getCode());
        if (findMember.isPresent()) {
            FriendInfoDto friendInfoDto = new FriendInfoDto();

            friendInfoDto.setFriendId(findMember.get().getId());
            friendInfoDto.setNickname(findMember.get().getNickname());
            friendInfoDto.setResolution(findMember.get().getResolution());

            return response.userFound(friendInfoDto);
        }

        return response.userNotFound();
    }


    /**
     * 친구 신청을 승인합니다.
     *
     * @param token
     * @param friendDto
     * @return
     */
    @PostMapping("/acceptRequest")
    ResponseEntity<Response> acceptFriendRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto) {
        AcceptFriendResponse response = new AcceptFriendResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) return response.unAuthorized();

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if (friend.isEmpty()) return response.friendNotFound();

        List<FriendRequest> friendRequest = friendRequestService.findFriendRequest(friend.get(), memberFind.get());
        if (friendRequest.size() == 0) return response.requestAcceptFailed();

        friendRequestService.acceptFriend(friendRequest.get(0));
        return response.requestAccepted();
    }

    /**
     * 친구 신청을 거절합니다.
     * @param token
     * @param friendDto
     * @return
     */
    @PostMapping("/rejectRequest")
    ResponseEntity<Response> rejectFriendRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendDto friendDto) {
        RejectFriendResponse response = new RejectFriendResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) return response.unAuthorized();

        // 친구 찾지 못한 경우
        Optional<Member> friend = memberService.getUserById(friendDto.getFriendId());
        if (friend.isEmpty()) return response.friendNotFound();

        List<FriendRequest> friendRequest = friendRequestService.findFriendRequest(friend.get(), memberFind.get());
        if (friendRequest.size() == 0) return response.requestRejectFailed();

        friendRequestService.rejectFriend(friendRequest.get(0));
        return response.rejectRequest();

    }

    /**
     * 친구 목록을 조회합니다.
     * @param token
     * @return
     */
    @GetMapping("/list")
    ResponseEntity<Response> getFriends(@RequestHeader("Authorization") String token){
        FriendListResponse response = new FriendListResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) return response.unAuthorized();

        List<FriendList> friendList = memberFind.get().getFriendList();
        List<FriendInfoDto> friendInfos = new ArrayList<>();
        for(FriendList friend: friendList) {
            Optional<Member> member = memberService.getUserById(friend.getFriendId());
            if (member.isEmpty()) {
                continue;
            }
            friendInfos.add(new FriendInfoDto(member.get().getId(), member.get().getNickname(), member.get().getResolution()));
        }
        return response.friendListSuccessful(friendInfos);
    }

    /**
     * 받은 친구 신청 목록을 조회합니다.
     * @param token
     * @return
     */
    @GetMapping("/requested")
    ResponseEntity<Response> getRequestedList(@RequestHeader("Authorization") String token){
        RequestedListResponse response = new RequestedListResponse();

        // 인증 실패
        Optional<Member> memberFind = memberService.verifyMember(token);
        if (memberFind.isEmpty()) response.unAuthorized();

        List<FriendRequest> requestedList = memberFind.get().getRequestedList();
        List<FriendInfoDto> friendInfos = new ArrayList<>();
        for(FriendRequest friendRequest:requestedList){
            Member friend = friendRequest.getRequester();
            friendInfos.add(new FriendInfoDto(friend.getId(), friend.getNickname(), friend.getResolution()));
        }
        return response.requestListSuccessful(friendInfos);
    }
}
