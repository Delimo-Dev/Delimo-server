package com.cos.delimo.repository.Impl;

import com.cos.delimo.dto.MemberDto;

import java.util.ArrayList;
import java.util.List;

public class MemoryMemberRepository {

    static public ArrayList<MemberDto> users;

    static{
        users = new ArrayList<>();
        users.add(new MemberDto("test1","닉넴", "1234"));
        users.add(new MemberDto("test2","닉넴", "1234"));
        users.add(new MemberDto("lee","닉넴", "1234"));
    }

    // CREATE
    public MemberDto insertUser(MemberDto user){
        users.add(user);
        return user;
    }

    // READ
    public List<MemberDto> getAllUsers(){
        return users;
    }

    public MemberDto getUserByUserEmail(String email){
        return users.stream()
                .filter(memberDto -> memberDto.getEmail().equals(email))
                .findAny()
                .orElse(new MemberDto("", "",""));
    }

    // UPDATE
    public void updateUserPw(String email, MemberDto memberDto){
        users.stream()
                .filter(memberDTO -> memberDTO.getEmail().equals(email))
                .findAny()
                .orElse(new MemberDto("","",""))
                .setPassword(memberDto.getPassword());
    }

    // DELETE
    public void deleteUser(String userEmail){
        users.removeIf(memberDto -> memberDto.getEmail().equals(userEmail));
    }
}
