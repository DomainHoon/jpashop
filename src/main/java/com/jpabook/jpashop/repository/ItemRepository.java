package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){            //item 은 jpa에 저장되기까지 값이 없음
        if(item.getId()==null){             //id가 없으면 새로만들어서 persist호출
            em.persist(item);
        }else {
            em.merge(item);                 //merge로 강제로 업데이트
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}
