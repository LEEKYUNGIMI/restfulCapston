package com.example.laby.service;

import com.example.laby.dto.HeartDTO;
import com.example.laby.dto.MemberDTO;
import com.example.laby.entity.HeartEntity;
import com.example.laby.entity.MemberEntity;
import com.example.laby.entity.PostEntity;
import com.example.laby.repository.HeartRepository;
import com.example.laby.repository.MemberRepository;
import com.example.laby.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 게시물을 좋아요한 회원 목록 조회
    public List<MemberDTO> findLikedMembersByPostId(Long postId){
        List<HeartEntity> heartEntities = heartRepository.findByPostId(postId);
        List<MemberDTO> likedMembers = new ArrayList<>();

        for (HeartEntity heartEntity:heartEntities){
            MemberEntity memberEntity = heartEntity.getMember();
            likedMembers.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return likedMembers;
    }

    // 사용자가 좋아요 누른 게시물 목록 조회
    public List<Long> findLikedPostIdByMemberId(Long memberId){
        List<HeartEntity> heartEntities = heartRepository.findByMemberId(memberId);
        List<Long> likedPostIds = new ArrayList<>();
        for (HeartEntity heartEntity:heartEntities){
            likedPostIds.add(heartEntity.getPost().getId());
        }
        return likedPostIds;
    }

    @Transactional
    public void addLike(HeartDTO heartDTO){
        Long memberId = heartDTO.getMemberId();
        Long postId = heartDTO.getPostId();

        // MemberEntity와 PostEntity를 찾음
        Optional<MemberEntity> optionalMember = memberRepository.findById(memberId);
        Optional<PostEntity> optionalPost = postRepository.findById(postId);

        // MemberEntity와 postEntity가 존재하면 HeartEntity를 생성하고 저장
        if (optionalMember.isPresent() && optionalPost.isPresent()){
            MemberEntity memberEntity = optionalMember.get();
            PostEntity postEntity = optionalPost.get();

            HeartEntity heartEntity = new HeartEntity();
            heartEntity.setMember(memberEntity);
            heartEntity.setPost(postEntity);


            heartRepository.save(heartEntity);
        }
        else {
            // 회원이나 게시물이 존재하지 않는 경우에 대한 처리
            throw new EntityNotFoundException("Member or Post not found");
        }
    }
}
