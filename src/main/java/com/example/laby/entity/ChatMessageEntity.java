package com.example.laby.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "chat_table")
@Setter
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String content;

    // 생성자, 게터 및 세터
}