package com.cos.delimo.Members.repository;

import com.cos.delimo.domain.Member;
import com.cos.delimo.repository.FriendRequestRepository;
import com.cos.delimo.repository.MemberRepository;
import com.cos.delimo.service.Impl.UuidService;
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
    private static MemberRepository memberRepository;
    @Autowired
    private static FriendRequestRepository friendRequestRepository;
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
