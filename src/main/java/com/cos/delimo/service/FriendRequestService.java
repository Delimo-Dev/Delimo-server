package com.cos.delimo.service;

import com.cos.delimo.domain.FriendRequest;
import com.cos.delimo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface FriendRequestService {
    Optional<Member> findByCode(String code);
    FriendRequest requestFriend(Member requester, Member requested);
    List<Long> getRequesterList(Member member);
    List<Long> getRequestedList(Member member);
    List<FriendRequest> getAllFriendRequest();
    List<FriendRequest> findFriendRequest(Member requester, Member requested);

    void acceptFriend(FriendRequest friendRequest);
    void rejectFriend(FriendRequest friendRequest);
}
