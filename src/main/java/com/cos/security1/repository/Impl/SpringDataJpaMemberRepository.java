package com.cos.security1.repository.Impl;

import com.cos.security1.domain.Member;
import com.cos.security1.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Member save(Member member);

    @Override
    Optional<Member> findByEmail(String email);

    @Override
    Optional<Member> findByCode(String code);

    @Override
    Optional<Member> findByToken(String token);

    @Query("update Member m set m.resolution = :resolution where m.id = :id")
    @Modifying
    void updateResolution(@Param("id") Long id, @Param("resolution") String resolution);
}
