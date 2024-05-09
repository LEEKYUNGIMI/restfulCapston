package com.example.laby.controller;

import com.example.laby.dto.ChatMessageDTO;
import com.example.laby.entity.ChatMessageEntity;
import com.example.laby.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/laby/message")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(@Payload String messageContent) {
        // 받은 메세지를 엔티티로 변환하여 저장
        ChatMessageEntity messageEntity = new ChatMessageEntity();

        messageEntity.setContent(messageContent);

        messageRepository.save(messageEntity);

        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setSender(messageEntity.getSender());
        messageDTO.setContent(messageEntity.getContent());
        return messageDTO;
    }
}
