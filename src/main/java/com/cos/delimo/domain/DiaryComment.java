package com.cos.delimo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private Long memberId;

    /**
     * 글(일기)와 N:1 관계
     */
    @ManyToOne
    @JoinColumn(name = "diary")
    @JsonIgnore
    private Diary diary;

    @Builder
    DiaryComment(Long memberId, String content, Diary diary) {
        this.memberId = memberId;
        this.content = content;
        this.diary = diary;
        this.createdDate = LocalDateTime.now();
    }
}
