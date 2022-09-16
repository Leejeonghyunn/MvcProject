package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

//@Data는 일반 도메인 모델에 사용하기에는 위험하다
@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //int인 경우 null이 들어갈 수 없다.
    private Integer quantity;

    public Item() { //기본 생성자

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
