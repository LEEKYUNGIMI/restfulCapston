package com.example.laby.controller;

import com.example.laby.dto.MemberDTO;
import com.example.laby.service.HeartService;
import com.example.laby.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "좋아요 API", description = "좋아요에 대한 설명입니다.")
public class HeartController {

    private final HeartService heartService;
    private final MemberService memberService;

// 좋아요 삭제 controller
@Operation(summary = "좋아요 삭제")
    @PostMapping("/laby/remove-heart")
public ResponseEntity<String> removeHeart(@RequestParam Long postId, HttpSession session){
    String loginEmail = (String) session.getAttribute("loginEmail");
    if (loginEmail == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
    }

    // 로그인한 사용자 ID 가져오기
    MemberDTO memberDTO = memberService.findByEmail(loginEmail);

    // 좋아요 삭제
    heartService.removeLike(memberDTO.getId(),postId);
    return ResponseEntity.ok("좋아요가 삭제되었습니다.");
}
}
