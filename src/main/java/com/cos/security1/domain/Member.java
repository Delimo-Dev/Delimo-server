package com.cos.security1.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "MEMBERS")
@ToString
@NoArgsConstructor
public class Member {
        @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "member_id")
        private Long id;

        @Column(name = "useremail", nullable = false)
        private String email;

        @Column(name = "userpw", nullable = false)
        private String password;

        @Column(name = "token", nullable = false)
        private String token;

        @Column(name = "usercode")
        private String code;

        private String resolution;

        @Builder
        public Member(String email, String password, String code, String token, String resolution) {
                this.email = email;
                this.password = password;
                this.code = code;
                this.token = token;
                this.resolution = resolution;
        }
}