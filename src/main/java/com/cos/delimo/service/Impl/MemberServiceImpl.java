package com.cos.delimo.service.Impl;

import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.*;
import com.cos.delimo.repository.MemberRepository;
import com.cos.delimo.security.SecurityService;
import com.cos.delimo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member insertUser(MemberDto user) {
        AuthenticationDto authenticationDto = new AuthenticationDto(user.getEmail(), user.getPassword());

        Member member = Member.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .token(new SecurityService().createToken(authenticationDto))
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .code(new UuidService().getUuid())
                .build();
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> verifyMember(String token) {
        String bearerToken = token.substring(7);
        return memberRepository.findByToken(bearerToken);
    }

    @Override
    public Optional<Member> getInfo(Long id) {
        return memberRepository.findById(id);
    }


    @Override
    public List<Member> getAllUsers(){
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getUserById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> getUserByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    @Override
    public void updateResolution(Long id, String resolution) {
        memberRepository.updateResolution(id, resolution);
    }

    @Override
    public List<Long> getFriendList(Member member){
        List<Long> friendIdList = new ArrayList<>();
        member.getFriendList().forEach((e)->friendIdList.add(e.getFriendId()));
        return friendIdList;
    }

    @Override
    public List<Long> getDiaryList(Member member) {
        List<Long> diaryIdList = new ArrayList<>();
        member.getDiaryList().forEach((e)->diaryIdList.add(e.getId()));
        return diaryIdList;
    }
}
