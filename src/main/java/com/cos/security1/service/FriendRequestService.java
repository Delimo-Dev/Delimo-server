package com.cos.security1.service;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;

import java.util.List;
import java.util.Optional;

public interface FriendRequestService {
    // 내가 보낸 친구 신청들
    List<FriendRequest> findByRequester(Long requesterId);
    // 나에게 온 친구 신청들
    List<FriendRequest> findByRequested(Long requestedId);

    // code로 친구 찾기
    Optional<Member> findByCode(String code);

    // 모든 친구 리스트 찾기
    List<FriendRequest> findAllFriends(Member member);

    // requester-> requested 친구 신청
    FriendRequest requestFriend(Member requester, Member requested);
    List<FriendRequest> getAllFriendRequest();
}
