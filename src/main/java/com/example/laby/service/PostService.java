package com.example.laby.service;

import com.example.laby.dto.PostDTO;
import com.example.laby.entity.PostEntity;
import com.example.laby.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void save(PostDTO postDTO){
        // 현재 시간 설정
        LocalDateTime now = LocalDateTime.now();

        PostEntity postEntity = PostEntity.toPostEntity(postDTO);
        postEntity.setLocalDateTime(now);

        postRepository.save(postEntity);
    }


    public List<PostDTO> findAll(){
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList){
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    }

    public PostDTO findById(Long id){
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()){
            return PostDTO.toPostDTO(optionalPostEntity.get());
        }
        else {
            return null;
        }
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }
}
