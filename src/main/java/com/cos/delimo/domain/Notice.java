package com.cos.delimo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "NOTICE_TBL")
@ToString
@NoArgsConstructor
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notice_content")
    private String content;

    @CreatedDate
    @Column(name = "NOTICE_CREATED_AT")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @Column(name = "is_read")
    private boolean isRead;

    @Builder
    public Notice(Member member, String content){
        this.member = member;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.isRead = false;
    }

    public void updateRead(){
        this.isRead = true;
    }
}
