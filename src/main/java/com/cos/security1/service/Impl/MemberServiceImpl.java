package com.cos.security1.service.Impl;

import com.cos.security1.domain.Member;
import com.cos.security1.dto.*;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.security.SecurityService;
import com.cos.security1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
        return memberRepository.findByToken(token);
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

}
