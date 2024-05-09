package com.example.laby.service;

import com.example.laby.dto.CommentDTO;
import com.example.laby.entity.CommentEntity;
import com.example.laby.entity.MemberEntity;
import com.example.laby.entity.PostEntity;
import com.example.laby.repository.CommentRepository;
import com.example.laby.repository.MemberRepository;
import com.example.laby.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 게시물에 댓글 단 회원 목록 조회
    public List<CommentDTO> findCommentByPostId(Long postId){
        List<CommentEntity> commentEntities = commentRepository.findByPostId(postId);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntities){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(commentEntity.getId());
            commentDTO.setMemberId(commentEntity.getMember().getId());
            commentDTO.setPostId(commentEntity.getPost().getId());
            commentDTO.setComment(commentEntity.getComment());

            //회원의 이름을 가져와서 설정
            commentDTO.setMemberName(commentEntity.getMember().getMemberName());
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    // 사용자가 작성한 댓글 목록 조회
    public List<CommentDTO> findCommentsByMemberId(Long memberId){
        List<CommentEntity> commentEntities = commentRepository.findByMemberId(memberId);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntities){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(commentEntity.getId());
            commentDTO.setMemberId(commentEntity.getMember().getId());
            commentDTO.setPostId(commentEntity.getPost().getId());
            commentDTO.setComment(commentEntity.getComment());

            commentDTO.setMemberName(commentEntity.getMember().getMemberName());
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    @Transactional
    public void save(CommentDTO commentDTO){
        Long memberId = commentDTO.getMemberId();
        Long postId = commentDTO.getPostId();

        Optional<MemberEntity> optionalMember = memberRepository.findById(memberId);
        Optional<PostEntity> optionalPost = postRepository.findById(postId);

        if(optionalMember.isPresent() && optionalPost.isPresent()){
            MemberEntity memberEntity=optionalMember.get();
            PostEntity postEntity = optionalPost.get();

            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setMember(memberEntity);
            commentEntity.setPost(postEntity);
            commentEntity.setComment(commentDTO.getComment());

            commentRepository.save(commentEntity);
        }else {
            // 회원이나 게시물이 존재하지 않는 경우에 대한 처리
            throw new EntityNotFoundException("Member or Post not found");
        }
    }

    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }
}
