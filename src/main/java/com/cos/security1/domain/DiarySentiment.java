package com.cos.security1.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SENTIMENT_TBL")
@NoArgsConstructor
@Getter
public class DiarySentiment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SENTIMENT_CODE")
    private int sentiment;

    @OneToOne
    @JoinColumn(name = "diary")
    private Diary diary;

    @Builder
    DiarySentiment(Diary diary, int sentiment){
        this.diary = diary;
        this.sentiment = sentiment;
    }
}
