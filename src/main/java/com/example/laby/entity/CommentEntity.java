package com.example.laby.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment_table")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private String comment;
}
