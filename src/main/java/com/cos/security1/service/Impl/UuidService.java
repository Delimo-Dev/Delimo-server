package com.cos.security1.service.Impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Getter
@Component
public class UuidService {

    private final String uuid;

    public UuidService(){
        uuid = createShortUuid();
    }

    private String createShortUuid() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        byte[] uuidStringBytes = uuidString.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(uuidStringBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            sb.append(String.format("%02x", hashBytes[j]));
        }
        return sb.toString();
    }
}
