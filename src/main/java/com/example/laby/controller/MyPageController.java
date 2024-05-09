package com.example.laby.controller;

import com.example.laby.dto.CommentDTO;
import com.example.laby.dto.MemberDTO;
import com.example.laby.dto.PostDTO;
import com.example.laby.service.CommentService;
import com.example.laby.service.HeartService;
import com.example.laby.service.MemberService;
import com.example.laby.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "마이페이지 API", description = "마이페이지에 대한 설명입니다.")
public class MyPageController {

    private final MemberService memberService;
    private final HeartService heartService;
    private final CommentService commentService;
    private final PostService postService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/laby/mypage")
    public ResponseEntity<?> showMyPage(HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        // 현재 로그인한 사용자 정보 가져오기
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);

        //현재 사용자가 좋아요 누른 게시물 목록 가져오기
        List<Long> userLikes = heartService.findLikedPostIdByMemberId(memberDTO.getId());

        //현재 사용자가 작성한 댓글 목록 가져오기
        List<CommentDTO> userComments = commentService.findCommentsByMemberId(memberDTO.getId());

        // 현재 사용자가 작성한 게시물 목록 가져오기
        List<PostDTO> userPosts = postService.findPostsByMemberId(memberDTO.getId());

        MyPageResponse response = new MyPageResponse(memberDTO.getMemberName(), userLikes, userComments, userPosts);
        return ResponseEntity.ok(response);
    }

    @Data
    @AllArgsConstructor
    static class MyPageResponse {
        private String memberName;
        private List<Long> userLikes;
        private List<CommentDTO> userComments;
        private List<PostDTO> userPosts;
    }
}
