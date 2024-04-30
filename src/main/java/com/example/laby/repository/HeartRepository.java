package com.example.laby.repository;

import com.example.laby.entity.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<HeartEntity, Long> {

        List<HeartEntity> findByPostId(Long postId);
        List<HeartEntity> findByMemberId(Long memberId);
}