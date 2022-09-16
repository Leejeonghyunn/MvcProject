package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * public BasicItemController(ItemRepository itemRepository) {  //싱글톤
     * this.itemRepository = itemRepository;
     * }
     **/

    /**
     * 상품 목록
     */
    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll(); //조회
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 상품 상세
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * 상품 등록 : Get으로 오면 addForm을 호출하고, addForm에서 Post로 오면 save()를 호출한다
     * -> 같은 url이지만 Http메서드로 구분해준다
     */
    @GetMapping("/add") //Form을 열때
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * @RequestParam : itemName 요청 파라미터 데이터를 해당 변수에 받는다
     * item객체를 생성하고 itemRepository를 통해서 저장한다(Model에 item이름으로 저장)
     * 저장된 item을 model에 담아서 '뷰'에 전달한다
     */
    //@PostMapping("/add") //실제 저장할때
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();

        item.setItemName(itemName); //@RequestParamd으로 파라미터가 오면 item객체를 생성한다 - 프로퍼티 접근법
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item); //저장

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @ModelAttribute : Item 객체를 생성하고, 요청 파라미터의 값을 '프로퍼티 접근법'으로 입력해준다
     * model.addAttribute를 실행 : Model에 지정한 객체를 자동으로 넣어준다(생략가능)
     */
    //@PostMapping("/add") //실제 저장할때
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model) {

        itemRepository.save(item); //저장

        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }

    /**
     * @ModelAttribute 이름 생략 가능 -> model에 저장될 때 클래스명을 사용한다. 클래스의 첫글자만 소문자로 변경해서 등록
     */
    //@PostMapping("/add") //실제 저장할때
    public String addItemV3(@ModelAttribute Item item,
                            Model model) {

        itemRepository.save(item); //저장

        return "basic/item";
    }

    /**
     * @ModelAttribute 전체 생략 가능
     */

    @PostMapping("/add") //실제 저장할때
    public String addItemV4(Item item, Model model) {

        itemRepository.save(item); //저장

        return "basic/item";
    }

    /**
     * 상품 수정 : 수정에 필요한 정보를 조회하고, 수정용 FormView를 호출한다
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {

        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
