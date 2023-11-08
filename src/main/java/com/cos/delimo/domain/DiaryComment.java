package com.cos.delimo.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "DIARY_COMMENT_TBL")
@ToString
@NoArgsConstructor
public class DiaryComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime createdDate;

    /**
     * 댓글 작성자와 1:1 관계
     */
    @OneToOne
    @JoinColumn(name = "member")
    private Member member;

    /**
     * 글(일기)와 N:1 관계
     */
    @ManyToOne
    @JoinColumn(name = "diary")
    private Diary diary;

    @Builder
    DiaryComment(Member member, String content, Diary diary) {
        this.member = member;
        this.content = content;
        this.diary = diary;
        this.createdDate = LocalDateTime.now();
    }
}
