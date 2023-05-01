package com.cos.security1.domain;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "friend_id")
    private Long friendId;

    @Builder
    FriendList(Member member, Long friendId){
        this.member = member;
        this.friendId = friendId;
    }
}
