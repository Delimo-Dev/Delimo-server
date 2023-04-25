package com.cos.security1.Members;

import com.cos.security1.domain.Member;
import com.cos.security1.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class MemberJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 새로운_회원_추가(){
        // DB 연결
        entityManager.persist(Member.builder().email("lyb2325@gmail.com").password("1234").token("1234").code("1234").build());
        Optional<Member> findMember = memberRepository.findByEmail("lyb2325@gmail.com");
        System.out.println("findMember = " + findMember.get());

        Assertions.assertThat(findMember.get().getEmail()).isEqualTo("lyb2325@gmail.com");
    }
}
