package com.example.laby.controller;

import com.example.laby.dto.CommentDTO;
import com.example.laby.dto.HeartDTO;
import com.example.laby.dto.MemberDTO;
import com.example.laby.dto.PostDTO;
import com.example.laby.entity.MemberEntity;
import com.example.laby.entity.PostEntity;
import com.example.laby.service.CommentService;
import com.example.laby.service.HeartService;
import com.example.laby.service.MemberService;
import com.example.laby.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시글 API", description = "게시글에 대한 설명입니다.")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final HeartService heartService;
    private final CommentService commentService;

    @Operation(summary = "게시글 등록폼 조회")
    @GetMapping("/laby/post")
    public ResponseEntity<?> postForm(HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        if (memberDTO == null) {
            return ResponseEntity.badRequest().body("로그인 정보를 확인할 수 없습니다.");
        }
        // 게시글 등록 폼을 보여줄 때는 ResponseEntity에 Model을 포함할 필요가 없습니다.
        // 따라서 간단히 문자열을 반환하여 처리합니다.
        return ResponseEntity.ok("게시글 등록 폼을 반환합니다.");
    }

    @Operation(summary = "게시글 등록요청")
    @PostMapping("/laby/post")
    //@RequestParam("image") MultipartFile image, 지움
    public ResponseEntity<?> savePost(@RequestBody PostDTO postDTO, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        if (memberDTO == null) {
            return ResponseEntity.badRequest().body("로그인 정보를 확인할 수 없습니다.");
        }
        // 파일 업로드를 처리하는 로직 추가
//        if (image != null && !image.isEmpty()) {
//            // 업로드된 파일을 처리하는 코드 추가
//            String imageURL = PostEntity.saveImage(image); // 이미지 저장하는 메소드 호출
//            postDTO.setImageURL(imageURL);
//        }
        postDTO.setMemberId(memberDTO.getId());
        postService.save(postDTO);
        return ResponseEntity.ok("게시물이 등록되었습니다.");
    }

    @Operation(summary = "게시글 목록 조회")
    @GetMapping("/laby/posts")
    public ResponseEntity<?> findAllPosts() {
        List<PostDTO> postDTOList = postService.findAll();
        return ResponseEntity.ok(postDTOList);
    }

    @Operation(summary = "게시글 상세페이지 조회")
    @GetMapping("/laby/post/{id}")
    public ResponseEntity<?> postDetail(@PathVariable Long id) {
        PostDTO postDTO = postService.findById(id);
        if (postDTO == null) {
            return ResponseEntity.notFound().build();
        }
        // 해당 게시물 좋아요한 회원 목록 가져오기
        List<MemberDTO> likedMembers = heartService.findLikedMembersByPostId(id);
        // 해당 게시물에 대한 댓글 목록 가져오기
        List<CommentDTO> comments = commentService.findCommentByPostId(id);
        // 필요한 데이터를 DTO에 담아 반환합니다.
        // 이 부분은 프론트엔드와 연동하여 적절한 데이터를 반환하도록 변경해야 합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("post", postDTO);
        response.put("likedMembers", likedMembers);
        response.put("comments", comments);

        return ResponseEntity.ok(response);
       // return ResponseEntity.ok("게시글 상세 정보를 반환합니다.");
    }

    @Operation(summary = "게시글에 좋아요 등록")
    @PostMapping("/laby/post/{id}/like")
    public ResponseEntity<String> addLike(@PathVariable Long id, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        HeartDTO heartDTO = new HeartDTO(memberDTO.getId(), id);
        heartService.addLike(heartDTO);
        return ResponseEntity.ok("좋아요가 추가되었습니다.");
    }

    @Operation(summary = "게시글에 댓글 등록")
    @PostMapping("/laby/post/{id}/comment")
    public ResponseEntity<String> addComment(@PathVariable Long id, @RequestBody Map<String, String> requestBody, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(id);
        commentDTO.setMemberId(memberDTO.getId());
        commentDTO.setComment(requestBody.get("comment"));
        commentService.save(commentDTO);
        return ResponseEntity.ok("댓글이 추가되었습니다.");
    }

    @Operation(summary = "게시글 편집페이지")
    @GetMapping("/laby/post/edit/{id}")
    public ResponseEntity<?> editPostForm(@PathVariable Long id, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        // 해당 id에 해당하는 게시물 정보 가져오기
        PostDTO postDTO = postService.findById(id);
        if (postDTO == null) {
            return ResponseEntity.notFound().build();
        }
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        if (memberDTO == null) {
            return ResponseEntity.badRequest().body("로그인 정보를 확인할 수 없습니다.");
        }
        // 여기서는 간단히 DTO를 반환하도록 했지만, 필요에 따라 모델 객체를 사용하여 화면에 데이터를 전달할 수도 있습니다.
        return ResponseEntity.ok(postDTO);
    }

    @Operation(summary = "게시글 편집 후 등록")
    @PostMapping("/laby/post/edit/{id}")
    public ResponseEntity<?> editPost(@PathVariable Long id, @RequestBody PostDTO postDTO, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        // 게시물 수정하는 로직
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        if (memberDTO == null) {
            return ResponseEntity.badRequest().body("로그인 정보를 확인할 수 없습니다.");
        }
        postDTO.setMemberId(memberDTO.getId());
        postService.save(postDTO);
        return ResponseEntity.ok("게시물이 수정되었습니다.");
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/laby/post/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }
        postService.deleteById(id);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }
}
