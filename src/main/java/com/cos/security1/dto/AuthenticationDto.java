package com.cos.security1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthenticationDto {
    String email;
    String password;
}
