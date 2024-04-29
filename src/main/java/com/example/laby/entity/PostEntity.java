package com.example.laby.entity;

import com.example.laby.dto.PostDTO;
import com.example.laby.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "post_table")
public class PostEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MemberEntity member;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime localDateTime = LocalDateTime.now();

    public static PostEntity toPostEntity(PostDTO postDTO){
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postDTO.getId());
        postEntity.setMemberId(postDTO.getMemberId());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setLocalDateTime(postDTO.getLocalDateTime());
        if (postDTO.getMember() != null) {
            postEntity.setMember(MemberEntity.toMemberEntity(postDTO.getMember())); // MemberDTO를 MemberEntity로 변환하여 저장
        }
        return postEntity;
    }
}
