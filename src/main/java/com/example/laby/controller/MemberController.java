package com.example.laby.controller;

import com.example.laby.dto.MemberDTO;
import com.example.laby.entity.MemberEntity;
import com.example.laby.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원에 대한 설명입니다.")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 등록 요청")
    @PostMapping("/laby/save")
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO){
        System.out.println("MemberController.save");
        System.out.println("memberDTO = "+memberDTO);
        memberService.save(memberDTO);

        return ResponseEntity.ok("User saved successfully");
    }

    @Operation(summary = "로그인 요청")
    @PostMapping("/laby/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult !=null){
            //login성공
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
            return ResponseEntity.ok("Login successful");
        }
        else {
            //login 실패
            return ResponseEntity.badRequest().body("Login failed");
        }
    }

    @Operation(summary = "회원 리스트 조회")
    @GetMapping("/laby/")
    public ResponseEntity<List<MemberDTO>> findAll(){
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.ok(memberDTOList);
    }

    @Operation(summary = "회원 상세 조회")
    @GetMapping("/laby/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable Long id){
        MemberDTO memberDTO = memberService.findById(id);
        return ResponseEntity.ok(memberDTO);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/laby/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/laby/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 세션에서 현재 로그인된 사용자 정보를 삭제
        session.removeAttribute("loginEmail");
        // 로그아웃 후 로그인 페이지로 리다이렉트
        return ResponseEntity.ok("Logout successful");
    }
}