package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;       //인젝션주입
    //jpa member 저장
    public void save(Member member){
        em.persist(member);
    }
    //jpa emp가 제공하는 단건 find 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);       //(type,pk)
    }
    //jpa list 조회
    public List<Member> findAll(){              //from 의 대상은 entiry
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }
    //이름으로 회원을 검색해야되는 경우
    public List<Member> findByName(String name){        //parameter binding을 통한 검색
        return em.createQuery("select m from Member m where m.name = :name",
                Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
