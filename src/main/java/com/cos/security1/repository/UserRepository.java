package com.cos.security1.repository;

import com.cos.security1.dto.UserDTO;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    static public ArrayList<UserDTO> users;

    static{
        users = new ArrayList<>();
        users.add(new UserDTO("test1","1234"));
        users.add(new UserDTO("test2","1234"));
        users.add(new UserDTO("lee","1234"));
    }

    // CREATE
    public UserDTO insertUser(UserDTO user){
        users.add(user);
        return user;
    }

    // READ
    public List<UserDTO> getAllUsers(){
        return users;
    }

    public UserDTO getUserByUserEmail(String email){
        return users.stream()
                .filter(userDTO -> userDTO.getUserEmail().equals(email))
                .findAny()
                .orElse(new UserDTO("",""));
    }

    // UPDATE
    public void updateUserPw(String email, UserDTO userDto){
        users.stream()
                .filter(userDTO -> userDTO.getUserEmail().equals(email))
                .findAny()
                .orElse(new UserDTO("",""))
                .setUserPw(userDto.getUserPw());
    }

    // DELETE
    public void deleteUser(String userEmail){
        users.removeIf(userDTO -> userDTO.getUserEmail().equals(userEmail));
    }
}
