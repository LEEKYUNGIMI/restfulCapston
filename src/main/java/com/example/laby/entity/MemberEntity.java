package com.example.laby.entity;


import com.example.laby.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

    public MemberDTO toMemberDTO(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(this.id);
        memberDTO.setMemberEmail(this.memberEmail);
        memberDTO.setMemberName(this.memberName);
        memberDTO.setMemberPassword(this.memberPassword);
        return memberDTO;
    }


}
