package com.cos.security1.Friend;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;
import com.cos.security1.dto.AuthenticationDto;
import com.cos.security1.repository.FriendRequestRepository;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.security.SecurityService;
import com.cos.security1.service.FriendRequestService;
import com.cos.security1.service.Impl.UuidService;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RequestFriendTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @BeforeEach
    void beforeEach(){
        Member member1 = Member.builder()
                .email("lyb2325@gmail.com")
                .password("1234")
                .token(new SecurityService().createToken(new AuthenticationDto("lyb2325@gmail.com", "1234")))
                .code(new UuidService().getUuid())
                .build();

        Member member2 = Member.builder()
                .email("lyb000@gmail.com")
                .password("1234")
                .code(new UuidService().getUuid())
                .token(new SecurityService().createToken(new AuthenticationDto("lyb0000@gmail.com", "1234")))
                .build();

        testEntityManager.persist(member1);
        testEntityManager.persist(member2);
    }

    @AfterEach
    void after_each(){
        testEntityManager.flush();
    }

    @Test
    void 친구_신청_보내기(){
        Optional<Member> findMember1 = memberRepository.findById(1L);
        Optional<Member> findMember2 = memberRepository.findById(2L);

        System.out.println("findMember1 = " + findMember1.get());
        System.out.println("findMember2 = " + findMember2.get());

        FriendRequest request = new FriendRequest(findMember1.get(), findMember2.get());
        friendRequestRepository.save(request);

        System.out.println(friendRequestRepository.findAllBy());
    }

    @Test
    void 중복_친구신청(){

    }

    @Test
    void 친구ID_탐색실패(){

    }

    @Test
    void 전송한_친구신청_확인하기(){

    }

    @Test
    void 신청받은_친구신청_확인하기(){

    }
}
