package com.example.laby.controller;

import com.example.laby.dto.MemberDTO;
import com.example.laby.dto.PostDTO;
import com.example.laby.entity.MemberEntity;
import com.example.laby.service.MemberService;
import com.example.laby.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/laby/post")
    public String postForm(Model model , HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return "redirect:/laby/login";
        }
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        model.addAttribute("postDTO",new PostDTO());
        return "post";
    }

    @PostMapping("/laby/post")
    public String savePost(@ModelAttribute PostDTO postDTO, HttpSession session){
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail==null){
            return "redirect:/laby/login";
        }
        MemberDTO memberDTO=memberService.findByEmail(loginEmail);
        postDTO.setMemberId(memberDTO.getId());
        postService.save(postDTO);
        return "redirect:/laby/posts";
    }

    @GetMapping("/laby/posts")
    public String findAllPosts(Model model) {
        List<PostDTO> postDTOList = postService.findAll();
        //List<MemberDTO> memberDTOList = memberService.findAll();
        //List<PostMemberDTO> postMemberDTOList = new ArrayList<>();
        for (PostDTO postDTO : postDTOList){
            if (postDTO.getMemberId() !=null){
                //MemberEntity memberEntity = memberService.findById(postDTO.getMemberId());
                //postDTO.setMember(memberEntity);
                MemberDTO memberDTO = memberService.findById(postDTO.getMemberId());
                postDTO.setMember(memberDTO);
            }
        }

        model.addAttribute("postList", postDTOList);

        return "postlist";
    }

    @GetMapping("/laby/post/{id}")
    public String postDetail(@PathVariable Long id, Model model){
        // 해당 id 에 해당하는 포스트 정보 조회
        PostDTO postDTO = postService.findById(id);

        //포스트 정보 없을 경우 에러 페이지로 리다이렉트

        if (postDTO == null){
            System.out.println("null입니다");
            return "redirect:/error";
        }


        model.addAttribute("post",postDTO);
        return "postDetail";

    }
}
