package com.cos.security1.Friend;

import com.cos.security1.domain.Member;
import com.cos.security1.dto.AuthenticationDto;
import com.cos.security1.repository.FriendRequestRepository;
import com.cos.security1.repository.MemberRepository;
import com.cos.security1.security.SecurityService;
import com.cos.security1.service.FriendRequestService;
import com.cos.security1.service.Impl.UuidService;
import com.cos.security1.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class FriendRequestServiceMockMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;


    @BeforeEach
    void beforeEach() {
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

        memberRepository.save(member1);
        memberRepository.save(member2);

        System.out.println(memberRepository.findAll());
    }

    @Test
    void 친구_신청_보내기(){
        Optional<Member> findMember1 = memberRepository.findById(1L);
        Optional<Member> findMember2 = memberRepository.findById(2L);

        System.out.println("findMember1 = " + findMember1.get());
        System.out.println("findMember2 = " + findMember2.get());

        friendRequestService.requestFriend(findMember1.get(), findMember2.get());
        System.out.println(friendRequestService.getAllFriendRequest());
    }

    @Test
    void 자기자신에게_친구신청_보내기_실패() throws Exception {
        Optional<Member> findMember1 = memberRepository.findById(1L);

        friendRequestService.requestFriend(findMember1.get(), findMember1.get());

        MvcResult result = mockMvc.perform(post("/friend/request")
                        .header("Authorization", "bearer"+findMember1.get().getToken())
                        .param("friendId", findMember1.get().getId().toString()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println("response = " + response);
    }

    @Test
    void 중복_친구신청() throws Exception {
        Optional<Member> findMember1 = memberRepository.findById(1L);
        Optional<Member> findMember2 = memberRepository.findById(2L);

        friendRequestService.requestFriend(findMember1.get(), findMember2.get());

        MvcResult result = mockMvc.perform(post("/friend/request")
                        .header("Authorization", "bearer"+findMember1.get().getToken())
                        .param("friendId", findMember1.get().getId().toString()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println("response = " + response);


    }

    @Test
    void 친구ID_탐색실패() throws Exception {
    }

    @Test
    void 전송한_친구신청_확인하기(){

    }

    @Test
    void 신청받은_친구신청_확인하기(){

    }
}
