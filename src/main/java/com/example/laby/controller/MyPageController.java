package com.example.laby.controller;

import com.example.laby.dto.MemberDTO;
import com.example.laby.service.HeartService;
import com.example.laby.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final HeartService heartService;

    @GetMapping("/laby/mypage")
    public String showMyPage(Model model, HttpSession session){
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return "redirect:/laby/login";
        }
        // 현재 로그인한 사용자 정보 가져오기
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);

        //현재 사용자가 좋아요 누른 게시물 목록 가져오기
        List<Long> likedPostIds = heartService.findLikedPostIdByMemberId(memberDTO.getId());

        //
        model.addAttribute("memberName", memberDTO.getMemberName());
        model.addAttribute("likedPostIds", likedPostIds);
        return "mypage";
    }

}
