package com.example.laby.repository;

import com.example.laby.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    // 추가적인 메서드들...
}
