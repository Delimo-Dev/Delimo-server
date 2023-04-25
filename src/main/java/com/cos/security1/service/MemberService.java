package com.cos.security1.service;

import com.cos.security1.domain.Member;
import com.cos.security1.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member insertUser(MemberDto user);
    List<Member> getAllUsers();
    Optional<Member> getUserById(Long id);
    Optional<Member> getUserByEmail(String email);
    void updateResolution(Long id, String resolution);
    Optional<Member> verifyMember(String token);
    Optional<Member> getInfo(Long id);
}
