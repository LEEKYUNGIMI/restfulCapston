package com.example.laby.controller;

import com.example.laby.dto.MemberDTO;
import com.example.laby.entity.MemberEntity;
import com.example.laby.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor //MemberService에 대한 맴버를 사용 가능
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/laby/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/laby/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.save");
        System.out.println("memberDTO = "+memberDTO);
        memberService.save(memberDTO);

        return "login";
    }

    @GetMapping("/laby/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/laby/login") //session : 다른 페이지로 이동할 때에도 로그인 유지
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult !=null){
            //login성공
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
            return "main";
        }
        else {
            //login 실패
            return "login";
        }
    }

    @GetMapping("/laby/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);
        return "list";
    }

    @GetMapping("/laby/{id}")
    public String findById(@PathVariable Long id , Model model){
        MemberDTO memberDTO = memberService.findById(id);
        //MemberEntity memberEntity = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    @GetMapping("/laby/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/laby/";
    }
}
