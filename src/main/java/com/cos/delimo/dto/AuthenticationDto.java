package com.cos.delimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthenticationDto {
    String email;
    String password;
}
