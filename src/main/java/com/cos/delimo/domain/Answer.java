package com.cos.delimo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "ANSWER_TBL")
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "ANSWER_CREATED_AT")
    private LocalDateTime createdDate;

    @OneToOne
    private Question question;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @Column
    private String answer;
}
