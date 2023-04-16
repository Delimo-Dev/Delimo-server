package com.cos.security1.service;

import com.cos.security1.dto.UserDTO;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserDTO inserUser(UserDTO user){
        return userRepository.insertUser(user);
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public UserDTO getUserByEmail(String email){
        return userRepository.getUserByUserEmail(email);
    }

    public void updateUserPw(String userEmail, UserDTO user){
        userRepository.updateUserPw(userEmail, user);
    }

    public void deleteUser(String userEmail){
        userRepository.deleteUser(userEmail);
    }
}
