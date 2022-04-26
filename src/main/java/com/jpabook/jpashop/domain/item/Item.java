package com.jpabook.jpashop.domain.item;

import static javax.persistence.FetchType.*;

import com.jpabook.jpashop.exception.NotEnoughStockException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { //추상체로 만듬 (구현체를 가지고 하기위해)
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(fetch = LAZY, mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==/ 데이터가 있는쪽에 넣는것이 best
    //재고수량을 증가시키는 로직
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity; //stockquantity가 line33줄에 있기때문에 이곳에 놓아야 관리가편함
        if(restStock < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
