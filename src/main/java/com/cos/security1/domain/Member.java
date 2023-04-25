package com.cos.security1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "MEMBER_TBL")
@ToString
@NoArgsConstructor
public class Member {
        @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;
        private String email;
        private String password;
        @Column(nullable = false)
        private String token;
        @Column(name = "USERCODE")
        private String code;
        private String resolution;

        @OneToMany(mappedBy = "member")
        private List<FriendList> friendList;

        @Builder
        public Member(String email, String password, String code, String token, String resolution) {
                this.email = email;
                this.password = password;
                this.code = code;
                this.token = token;
                this.resolution = resolution;
        }
}