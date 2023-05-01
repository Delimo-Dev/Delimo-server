package com.cos.security1.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MemberDto {
    private String email;
    private String nickname;
    private String password;
}
