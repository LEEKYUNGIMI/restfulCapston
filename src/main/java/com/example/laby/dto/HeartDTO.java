package com.example.laby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HeartDTO {

    private Long memberId;
    private Long postId;

    public HeartDTO(Long memberId, Long postId){
        this.memberId=memberId;
        this.postId=postId;
    }
}
