package com.example.laby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessageDTO {
    private String sender;
    private String content;

    // 생성자, 게터 및 세터
}
