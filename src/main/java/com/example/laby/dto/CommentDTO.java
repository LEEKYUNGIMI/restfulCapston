package com.example.laby.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentDTO {

    private Long id; // 댓글의 ID
    private Long postId; // 댓글이 속한 게시물의 ID
    private Long memberId; // 댓글을 작성한 회원의 ID
    private String comment; // 댓글 내용
    private String memberName;
}
