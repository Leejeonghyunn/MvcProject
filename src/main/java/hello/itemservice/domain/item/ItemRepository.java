package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //@ComponentScan의 대상이 된다
public class ItemRepository {

    //실무에서는 HashMap, sequance를 사용하지말고 ConcurrentHashMap을 사용 -> 동시에 접근하면 값이 꼬일 수 있다
    private static final Map<Long, Item> store = new HashMap<>(); //static사용 -> new 사용 방지
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);

        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); //감싸서 반환하면 ArrayList에 다른 값을 넣어도 store에는 값이 변하진 않는다 - 안전
    }

    /**
     * 상품 업데이트
     */
    public void update(Long itemId, Item updateParam) {

        Item findItem = findById(itemId); //itemId를 찾는다

        findItem.setItemName(updateParam.getItemName()); //설계상 itemParamDto를 만들어 3개를 따로 객체로 만드는것이 낫다
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear(); //HashMap<>(); 의 데이터를 다 삭제시킨다
    }
}
