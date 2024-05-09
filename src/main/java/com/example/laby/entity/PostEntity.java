package com.example.laby.entity;

import com.example.laby.dto.PostDTO;
import com.example.laby.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "post_table")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_member_id")
    private Long memberId;

    @ManyToOne
    //@JoinColumn(name = "member_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity member;

    @Column
    private String title;

    @Column
    private String content;

//    @Column
//    private String imageUrl;

    @Column
   private String image;

    @Column
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Column
    private String link;

    public static PostEntity toPostEntity(PostDTO postDTO){
        if (postDTO == null) {
            return null;
        }
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postDTO.getId());
        postEntity.setMemberId(postDTO.getMemberId());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        //postEntity.setImageUrl(postDTO.getImage() != null ? saveImage(postDTO.getImage()) : null);
        //postEntity.setImageUrl(postDTO.getImageURL());
        postEntity.setImage(postDTO.getImage());

        postEntity.setLocalDateTime(postDTO.getLocalDateTime());
        postEntity.setLink(postDTO.getLink());
        if (postDTO.getMember() != null) {
            postEntity.setMember(MemberEntity.toMemberEntity(postDTO.getMember())); // MemberDTO를 MemberEntity로 변환하여 저장
        }
        return postEntity;
    }

    public static String saveImage(MultipartFile imageFile){

        // 업로드할 디렉토리 경로 설정
        String uploadDir = "src/main/resources/static/upload/";

        File dir = new File(uploadDir);
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            //이미지 파일의 확장자 추출
            String originalFileName = imageFile.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

            //랜덤한 파일명 생성
            String fileName = UUID.randomUUID().toString() + extension;

            //파일 경로 설정
            Path filePath = Paths.get(uploadDir + fileName);

            // 이미지 파일을 지정한 경로에 저장
            Files.write(filePath, imageFile.getBytes());

            // 저장된 이미지의 경로를 반환
           // return uploadDir + fileName;
            return fileName;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        }
    }
