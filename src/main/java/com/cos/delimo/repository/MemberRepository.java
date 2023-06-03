package com.cos.delimo.repository;

import com.cos.delimo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByCode(String code);
    Optional<Member> findByToken(String token);
    List<Member> findAll();
    void updateResolution(Long id, String resolution);
}
