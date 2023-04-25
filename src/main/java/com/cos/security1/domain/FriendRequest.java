package com.cos.security1.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "FRIEND_REQUEST_TBL")
@NoArgsConstructor
@ToString
public class FriendRequest implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester")
    private Member requester;

    @ManyToOne
    @JoinColumn(name = "requested")
    private Member requested;

    @Builder
    public FriendRequest(Member requester, Member requested){
        this.requester = requester;
        this.requested = requested;
    }
}
