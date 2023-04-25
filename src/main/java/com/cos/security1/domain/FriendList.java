package com.cos.security1.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FRIEND_TBL")
@NoArgsConstructor
public class FriendList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "friend_id")
    private Long friendId;
}
