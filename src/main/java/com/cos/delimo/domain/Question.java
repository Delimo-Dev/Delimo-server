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
@Table(name = "QUESTION_TBL")
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "QUESTION_DATE")
    private LocalDateTime createdDate;

    @Column
    private String question;
}
