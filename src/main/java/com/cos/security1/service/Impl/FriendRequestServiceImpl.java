package com.cos.security1.service.Impl;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;
import com.cos.security1.repository.FriendRequestRepository;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.service.FriendRequestService;
import com.cos.security1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return friendRequestRepository.save(friendRequest);
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
    public Optional<Member> findByCode(String code) {
        return memberRepository.findByCode(code);
    }

    @Override
    public List<FriendRequest> findAllFriends(Member member) {
        return friendRequestRepository.findAllByRequester(member.getId());
    }

    @Override
    public List<FriendRequest> getAllFriendRequest() {
        return friendRequestRepository.findAllBy();
    }



}
