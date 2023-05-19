package com.cos.security1.diary;

import com.cos.security1.domain.Diary;
import com.cos.security1.domain.Member;
import com.cos.security1.repository.DiaryRepository;
import com.cos.security1.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
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
public class DiaryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;


    @Test
    void 회원의_일기_추가() {
        Member newMember = Member.builder()
                .email("lyb2325@gmail.com")
                .password("1234")
                .token("1234")
                .code("1234")
                .build();

        // DB 연결
        entityManager.persist(newMember);
        Optional<Member> findMember = memberRepository.findByEmail("lyb2325@gmail.com");
        System.out.println("findMember = " + findMember.get());

        Diary newDiary = Diary.builder()
                .member(newMember)
                .content("오늘의 일기")
                .build();
        entityManager.persist(newDiary);

        List<Diary> diaries = diaryRepository.findAllBy();
        for (Diary diary : diaries) {
            System.out.println(diary.getContent());
        }
    }

}
