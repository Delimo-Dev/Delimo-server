package com.cos.security1.controller;

import com.cos.security1.dto.AuthenticationDto;
import com.cos.security1.security.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Transactional
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/create/token")
    public Map<String, Object> createToken(@RequestParam AuthenticationDto auth){
        String token = securityService.createToken(auth);
        Map<String, Object> map = new LinkedHashMap<>();
        return map;
    }

    @GetMapping("/get/subject")
    public Map<String, Object> getSubject(@RequestParam(value = "token") String token){
        String subject = securityService.getSubject(token);
        Map<String, Object> map = new LinkedHashMap<>();
        return map;
    }
}
