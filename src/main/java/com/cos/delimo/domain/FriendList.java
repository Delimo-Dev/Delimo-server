package com.cos.delimo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FRIEND_TBL")
@NoArgsConstructor
@Getter
public class FriendList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MEMBER")
    private Member member;

    @Column(name = "FRIEND")
    private Long friendId;

    @Builder
    FriendList(Member member, Long friendId){
        this.member = member;
        this.friendId = friendId;
    }
}
