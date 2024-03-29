package com.cos.delimo.Friend;

import com.cos.delimo.domain.FriendRequest;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.AuthenticationDto;
import com.cos.delimo.repository.FriendRequestRepository;
import com.cos.delimo.repository.MemberRepository;
import com.cos.delimo.security.SecurityService;
import com.cos.delimo.service.impl.UuidService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
        Optional<Member> findMember1 = memberRepository.findByEmail("lyb2325@gmail.com");
        Optional<Member> findMember2 = memberRepository.findByEmail("lyb000@gmail.com");

        System.out.println("findMember1 = " + findMember1.get());
        System.out.println("findMember2 = " + findMember2.get());

        FriendRequest request = new FriendRequest(findMember1.get(), findMember2.get());
        testEntityManager.persist(request);

        System.out.println("findAll " + friendRequestRepository.findAllBy());
    }

    @Test
    void 자기자신에게_친구신청_보내기_실패(){
        Optional<Member> findMember1 = memberRepository.findById(1L);

        FriendRequest friendRequest = new FriendRequest(findMember1.get(), findMember1.get());

        testEntityManager.persist(friendRequest);
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
