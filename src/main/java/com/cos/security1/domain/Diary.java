package com.cos.security1.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "DIARY_TBL")
@NoArgsConstructor
@Getter
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @Column(name = "content")
    private String content;

    @Column(name = "privacy_code")
    private int privacy;

    @CreatedDate
    @Column(name = "DIARY_CREATED_AT")
    private LocalDateTime createdDate;

    @Builder
    Diary(Member member, String content, int privacy){
        this.member = member;
        this.content = content;
        this.privacy = privacy;
        this.createdDate = LocalDateTime.now();
    }

}
