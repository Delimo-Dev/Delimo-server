package com.cos.delimo.controller;

import com.cos.delimo.domain.Member;
import com.cos.delimo.service.ExcelService;
import com.cos.delimo.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/download")
public class ExcelController {
    private final MemberService memberService;
    private final ExcelService excelService;


    @Autowired
    public ExcelController(MemberService memberService, ExcelService excelService) {
        this.memberService = memberService;
        this.excelService = excelService;
    }

    @GetMapping
    public String getUsers(Model model){
        List<Member> members = memberService.getAllUsers();
        model.addAttribute("members", members);
        return "members";
    }


    @PostMapping(value = "/export")
    public void exportMemberInfo(HttpServletResponse response) throws IOException{
        excelService.exportToExcel(response);
    }

}
