package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)      //데이터 변경이 필요한경우 반드시 필요 spring의 transactional권장
@RequiredArgsConstructor            //final이 있는 필드만 가지고 생성자 생성
public class MemberService {
          //spring 에 등록되어 있는 필드 인젝션
    //final 어노테이션을 넣으면 컴파일시점을 체크가능
    private final MemberRepository memberRepository;
         
    //생성자 인젝션 allargs느낌
    //public MemberService(MemberRepository memberRepository) {
    //    this.memberRepository = memberRepository; }

    /*
    @Autowired
        //이렇게 하는경우 메서드를 통해 불러들이기에 수정이가능하다. testcode용도/ 하지만 위와같은경우 필드에서 부르기에
        public void setMemberRepository(MemberRepository memberRepository){
            this.memberRepository = memberRepository;
        }
    */
    //회원가입
    @Transactional
    public Long join(Member member){ //멤버 객체를 넘김
        validateDuplicateMember(member);//중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //동시의 가입하는 경우도 있기에 최고의 방어책으로 name에 유니크 제약을 검
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
    //읽는 작업에는 readonlyu추가
    //회원 전체 조회
    // @Transactional(readOnly = true)   jpa가 조회하는곳에서 성능 최적화 영속성컨텐츠의 더티체킹을 안하는 이점...db에선 리소스별로안씀
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
