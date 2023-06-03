package com.cos.delimo.service.Impl;

import com.cos.delimo.domain.*;
import com.cos.delimo.repository.*;
import com.cos.delimo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final MemberRepository memberRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendListRepository friendListRepository;

    @Autowired
    public FriendRequestServiceImpl(MemberRepository memberRepository, FriendRequestRepository friendListRepository, FriendListRepository friendListRepository1) {
        this.memberRepository = memberRepository;
        this.friendRequestRepository = friendListRepository;
        this.friendListRepository = friendListRepository1;
    }

    @Override
    public Optional<Member> findByCode(String code) {
        return memberRepository.findByCode(code);
    }

    @Override
    public FriendRequest requestFriend(Member requester, Member requested) {
        FriendRequest friendRequest = FriendRequest.builder()
                .requester(requester)
                .requested(requested)
                .build();

        List<FriendRequest> findRequests = friendRequestRepository.findRequest(requester, requested);
        if(findRequests.size() == 0) {
            return friendRequestRepository.save(friendRequest);
        }
        return null;
    }

    @Override
    public List<Long> getRequesterList(Member member){
        List<Long> requestedIdList = new ArrayList<>();
        List<FriendRequest> requestList = getAllFriendRequest();
        for(FriendRequest request:requestList){
            if(request.getRequester().getId().equals(member.getId())){
                requestedIdList.add(request.getRequested().getId());
            }
        }
        return requestedIdList;
    }

    @Override
    public List<Long> getRequestedList(Member member){
        List<Long> requesterIdList = new ArrayList<>();
        List<FriendRequest> requestList = getAllFriendRequest();

        for(FriendRequest request : requestList){
            if (request.getRequested().getId().equals(member.getId())){
                requesterIdList.add(request.getRequester().getId());
            }
        }
        return requesterIdList;
    }

    @Override
    public List<FriendRequest> getAllFriendRequest() {
        return friendRequestRepository.findAllBy();
    }

    @Override
    public List<FriendRequest> findFriendRequest(Member requester, Member requested) {
        return friendRequestRepository.findRequest(requester, requested);
    }

    @Override
    public void acceptFriend(FriendRequest friendRequest) {
        // 친구 리스트에 추가
        FriendList friendListForRequester = FriendList.builder()
                .member(friendRequest.getRequester())
                .friendId(friendRequest.getRequested().getId())
                .build();

        FriendList friendListForRequested = FriendList.builder()
                .member(friendRequest.getRequested())
                .friendId(friendRequest.getRequester().getId())
                .build();

        friendListRepository.save(friendListForRequested);
        friendListRepository.save(friendListForRequester);

        // 해당 FriendRequest 삭제
        friendRequestRepository.delete(friendRequest);
    }

    @Override
    public void rejectFriend(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequest);
    }


}
