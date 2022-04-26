package com.jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //public은 안되지만 protected으로 설정하는것이 안전하다
    protected Address() {
    }
    //setter를 제거하고 생성자에서 값을 모두 초기화해서 변경불가능한 클래스로 생성 jpa스펙상 엔티티나 임베디드타입은 public or protected로설정
    //이러한 제약의 이유는 jpa구현라이브러리가 객체를 생성시 리플랙션같은 기술을 사용할수 있게 지원해야기 때문
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
