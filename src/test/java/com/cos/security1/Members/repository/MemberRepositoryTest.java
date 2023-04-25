package com.cos.security1.Members.repository;

import com.cos.security1.domain.Member;
import com.cos.security1.dto.AuthenticationDto;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.security.SecurityService;
import com.cos.security1.service.Impl.UuidService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 두명의_회원_신규회원가입(){
        Member member = Member.builder()
                .email("lyb2325@gmail.com")
                .password("1234")
                .code(new UuidService().getUuid())
                .resolution("오늘도 화이팅")
                .build();

        Member member2 = Member.builder()
                .email("2l3y2b5@naver.com")
                .password("1234")
                .code(new UuidService().getUuid())
                .resolution("꾸준하게")
                .build();

        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();
        for(Member memberFind:members){
            System.out.println("member = " + memberFind);
        }

        Assertions.assertThat(members.size()).isEqualTo(2);
    }
}
