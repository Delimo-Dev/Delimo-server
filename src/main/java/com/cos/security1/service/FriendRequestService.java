package com.cos.security1.service;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;

import java.util.List;
import java.util.Optional;

public interface FriendRequestService {
    List<FriendRequest> findByRequester(Long requesterId);
    List<FriendRequest> findByRequested(Long requestedId);
    Optional<Member> findByCode(String code);
    List<Long> getFriendList(Member member);
    List<Long> getRequesterList(Member member);
    List<Long> getRequestedList(Member member);
    FriendRequest requestFriend(Member requester, Member requested);
    List<FriendRequest> getAllFriendRequest();
}
