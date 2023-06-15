package com.cos.delimo.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "POST_TBL")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "POST_CREATED_AT")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @Column
    private String content;
}
