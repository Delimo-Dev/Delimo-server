package com.cos.delimo.service;

import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member insertUser(MemberDto user);
    List<Member> getAllUsers();
    Optional<Member> getUserById(Long id);
    Optional<Member> getUserByEmail(String email);
    void updateResolution(Long id, String resolution);
    Optional<Member> verifyMember(String token);
    boolean verifyPassword(Member member, String password);
    Optional<Member> verifyPasswordWithToken(Member member, String password);
    Optional<Member> getInfo(Long id);
    List<Long> getFriendList(Member member);
    List<Long> getDiaryList(Member member);
}
