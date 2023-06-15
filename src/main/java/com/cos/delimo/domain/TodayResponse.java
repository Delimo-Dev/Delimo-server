package com.cos.delimo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TODAY_RESPONSE")
@ToString
@NoArgsConstructor
public class TodayResponse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @OneToOne
    private Diary diary;

    @OneToOne
    private Answer answer;

    @CreatedDate
    @Column(name = "response_created_at")
    private LocalDateTime createdTime;
}
