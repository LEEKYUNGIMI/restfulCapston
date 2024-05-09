package com.example.laby.dto;

import com.example.laby.entity.MemberEntity;
import com.example.laby.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDTO {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    //private MultipartFile image;
    //private String imageURL;

    private String image;
    private String link;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") // 초를 없애고 시, 분까지만 표시
    private LocalDateTime localDateTime;

    private MemberDTO member;


    public static PostDTO toPostDTO(PostEntity postEntity){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setMemberId(postEntity.getMemberId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        //postDTO.setImageURL(postEntity.getImageUrl());
        //postDTO.setImageUrl(postEntity.getImageUrl());
        postDTO.setImage(postEntity.getImage());
        postDTO.setLocalDateTime(postEntity.getLocalDateTime());
        postDTO.setLink(postEntity.getLink());
        return postDTO;
    }

}
