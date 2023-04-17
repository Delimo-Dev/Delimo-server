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

        @Column(name = "usercode")
        private String code;

        private String resolution;

        @Builder
        public Member(String email, String password, String code, String resolution) {
                this.email = email;
                this.password = password;
                this.code = code;
                this.resolution = resolution;
        }
}