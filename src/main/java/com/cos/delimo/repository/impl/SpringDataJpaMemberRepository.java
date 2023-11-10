package com.cos.delimo.repository.impl;

import com.cos.delimo.domain.Member;
import com.cos.delimo.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Query("update Member m set m.resolution = :resolution where m.id = :id")
    @Modifying
    void updateResolution(@Param("id") Long id, @Param("resolution") String resolution);
}
