package com.example.laby.controller;

import com.example.laby.dto.CommentDTO;
import com.example.laby.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 API", description = "댓글에 대한 설명입니다.")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/laby/comment/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
