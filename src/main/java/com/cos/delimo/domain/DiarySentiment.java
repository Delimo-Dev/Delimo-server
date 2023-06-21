package com.cos.delimo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "diary")
    private Diary diary;

    @Builder
    DiarySentiment(Diary diary, int sentiment){
        this.diary = diary;
        this.sentiment = sentiment;
    }
}
