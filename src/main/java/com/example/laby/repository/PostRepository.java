package com.example.laby.repository;

import com.example.laby.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    List<PostEntity> findByMemberId(Long memberId);

}
