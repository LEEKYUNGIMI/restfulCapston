package com.example.laby.service;

import com.example.laby.dto.MemberDTO;
import com.example.laby.entity.MemberEntity;
import com.example.laby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO){
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO){
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if (byMemberEmail.isPresent()){
            //조회 결과가 있다
            MemberEntity memberEntity = byMemberEmail.get(); //optional 에서 꺼냄
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                //비밀번호 일치
                //entity-> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }
            else {
                //비밀번호 불일치
                return null;
            }
        }
        else {
            // 조회 결과가 없다
            return null;
        }
    }

    public List<MemberDTO> findAll(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        //Controller로 dto 변환해서 줘야 함
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity:memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    public  MemberDTO findById(Long id){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
            //optional을 벗겨내서 entity -> dto변환
        }
        else {
            return null;
        }
    }

    public void deleteById(Long id){
        memberRepository.deleteById(id);
    }

}
