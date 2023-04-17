package com.cos.security1.service.Impl;

import com.cos.security1.domain.Member;
import com.cos.security1.dto.MemberDto;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.security.SecurityService;
import com.cos.security1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Member member = Member.builder()
                .email(user.getEmail())
                .password(new SecurityService().createToken(user.getPassword(), 1000 * 60 * 20))
                .code(new UuidService().getUuid())
                .build();
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> verifyMember(String password) {
        System.out.println("password = " + password);
        return memberRepository.findByPassword(password);
    }

    @Override
    public List<Member> getAllUsers(){
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getUserByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    @Override
    public void updateResolution(Long id, String resolution) {
        System.out.println("id = " + id);
        System.out.println("resolution = " + resolution);
        memberRepository.updateResolution(id, resolution);
    }

}
