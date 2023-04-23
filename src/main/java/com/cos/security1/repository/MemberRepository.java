package com.cos.security1.repository;

import com.cos.security1.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPassword(String password);
    Optional<Member> findByCode(String code);
    Optional<Member> findByToken(String token);
    List<Member> findAll();
    void updateResolution(Long id, String resolution);
}
