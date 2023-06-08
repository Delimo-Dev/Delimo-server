package com.cos.delimo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Table(name = "MEMBER_TBL")
@ToString
@NoArgsConstructor
public class Member {
        @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String email;

        private String nickname;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String token;

        @Column(name = "USERCODE")
        private String code;

        private String resolution;

        @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
        private List<FriendList> friendList;

        @OneToMany(mappedBy = "requested", fetch = FetchType.LAZY)
        private List<FriendRequest> requestedList;

        @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
        private List<FriendRequest> requesterList;

        @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
        private List<TodayResponse> responseList;

        @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
        private List<Diary> diaryList;

        @Builder
        public Member(String email, String nickname, String password, String code, String token, String resolution) {
                this.email = email;
                this.nickname = nickname;
                this.password = password;
                this.code = code;
                this.token = token;
                this.resolution = resolution;
        }
}