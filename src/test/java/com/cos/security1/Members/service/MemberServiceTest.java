package com.cos.security1.Members.service;

import com.cos.security1.domain.Member;
import com.cos.security1.dto.MemberDto;
import com.cos.security1.service.MemberService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    void 이메일_중복_회원가입(){
        MemberDto memberDto = new MemberDto("lyb2325@gmail.com", "옙", "1234");
        MemberDto memberDto2 = new MemberDto("lyb2325@gmail.com", "옙", "1234");


        try {
            memberService.insertUser(memberDto);
            memberService.insertUser(memberDto2);
        }catch(Exception e){
            Assertions.assertThat(e)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("이미 사용 중인 이메일입니다.");
        }
    }

    @Test
    void 로그인_회원인증_실패(){
        // token 일치하는 회원 탐색 실패
    }
    
    @Test
    void 로그인_회원인증_성공(){
        
    }

    @Test
    void 오늘의_다짐_수정성공() {

    }
}
