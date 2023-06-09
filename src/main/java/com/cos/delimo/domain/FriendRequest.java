package com.cos.delimo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FRIEND_REQUEST_TBL")
@NoArgsConstructor
@Getter
public class FriendRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
