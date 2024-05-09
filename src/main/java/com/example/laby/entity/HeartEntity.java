package com.example.laby.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "heart_table")
public class HeartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    //@JoinColumn(name = "member_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    //@JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PostEntity post;

}
