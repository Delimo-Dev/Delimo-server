package com.cos.security1.controller;

import com.cos.security1.dto.UserDTO;
import com.cos.security1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("")
    public UserDTO insertUser(@RequestBody UserDTO user){
        return userService.inserUser(user);
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userEmail}")
    public UserDTO getUserByEmail(@PathVariable String userEmail){
        return userService.getUserByEmail(userEmail);
    }

    @PutMapping("/{userEmail}")
    public void updateUserPw(@PathVariable String userEmail, @RequestBody UserDTO user){
        userService.updateUserPw(userEmail, user);
    }

    @DeleteMapping("/{userEmail}")
    public void deleteUser(@PathVariable String userEmail){
        userService.deleteUser(userEmail);
    }


}
