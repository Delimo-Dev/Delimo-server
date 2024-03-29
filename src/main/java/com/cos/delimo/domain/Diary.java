package com.cos.delimo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "DIARY_TBL")
@NoArgsConstructor
@Getter
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "member")
    @JsonIgnore
    private Member member;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "privacy_code")
    private int privacy;

    @Column(name = "visited")
    private Integer visited;

    @CreatedDate
    @Column(name = "DIARY_CREATED_AT")
    private LocalDateTime createdDate;

    @OneToOne
    @JoinColumn(name = "diarySentiment")
    private DiarySentiment diarySentiment;


    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DiaryComment> comments;

    @Builder
    Diary(Member member, String content, int privacy){
        this.member = member;
        this.content = content;
        this.privacy = privacy;
        this.createdDate = LocalDateTime.now();
    }

    public void updateSentiment(DiarySentiment diarySentiment){
        this.diarySentiment = diarySentiment;
    }

    public void updateVisited(){
        if (this.visited == null){
            this.visited = 1;
        }
        else{
            this.visited += 1;
        }
    }
}
