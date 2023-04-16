package com.cos.security1.UserRepositoryTest.UUID;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CreateUuid {

    @Test
    void createShortUuid(){
        for (int i=0;i<10;i++) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            byte[] uuidStringBytes = uuidString.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes;

            try{
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                hashBytes = messageDigest.digest(uuidStringBytes);
            }catch (NoSuchAlgorithmException e){
                throw new RuntimeException(e);
            }
            StringBuilder sb = new StringBuilder();
            for (int j=0;j<4;j++){
                sb.append(String.format("%02x", hashBytes[j]));
            }
        }
    }
}
