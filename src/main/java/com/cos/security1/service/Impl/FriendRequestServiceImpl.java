package com.cos.security1.service.Impl;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;
import com.cos.security1.repository.FriendRequestRepository;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.service.FriendRequestService;
import com.cos.security1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private MemberService memberService;
    private final MemberRepository memberRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendRequestServiceImpl(MemberService memberService, MemberRepository memberRepository, FriendRequestRepository friendListRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.friendRequestRepository = friendListRepository;
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
    public List<FriendRequest> findByRequester(Long requesterId) {
        return friendRequestRepository.findAllByRequester(requesterId);
    }

    @Override
    public List<FriendRequest> findByRequested(Long requestedId) {
        return friendRequestRepository.findAllByRequested(requestedId);
    }

    @Override
    public List<Long> getFriendList(Member member){
        List<Long> friendIdList = new ArrayList<>();
        member.getFriendList().forEach((e)->friendIdList.add(e.getFriendId()));
        return friendIdList;
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
    public Optional<Member> findByCode(String code) {
        return memberRepository.findByCode(code);
    }

    @Override
    public List<FriendRequest> getAllFriendRequest() {
        return friendRequestRepository.findAllBy();
    }



}
