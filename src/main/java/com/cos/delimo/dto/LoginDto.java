package com.cos.delimo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    String email;
    String password;
}
