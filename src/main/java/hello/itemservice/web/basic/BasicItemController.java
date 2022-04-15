package hello.itemservice.web.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

	private final ItemRepository itemRepository;
	
	public BasicItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@GetMapping
	public String items(Model model)
	{
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		
		return "basic/items";
	}

	@GetMapping("{itemId}")
	public String item(@PathVariable Long itemId, Model model)
	{
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		
		return "basic/item";
	}
	
	@GetMapping("/add")
	public String addForm()
	{
		return "basic/addForm";
	}
	
//	@PostMapping("/add")
	public String saveV1(@RequestParam String itemName,
						@RequestParam int price,
						@RequestParam int quantity,
						Model model)
	{
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuantity(quantity);
		
		itemRepository.save(item);
		
		model.addAttribute("item", item);

		return "basic/item";
	}

//	@PostMapping("/add")
	public String saveV2(@ModelAttribute("item") Item item)
	{
		itemRepository.save(item);
		// @ModelAttribute로 받을때 "item"을 명시하여 Model에 item 개체가 "자동추가"
		// 따라서, 아래 라인이 필요없게 됨.
		// model.addAttribute("item", item); 
		
		return "basic/item";
	}
	
//	@PostMapping("/add")
	public String saveV3(@ModelAttribute Item item)
	{
		itemRepository.save(item);
//		model.addAttribute("item", item); // Item -> item으로 Model에 자동추가됨.
		
		return "basic/item";
	}
	
	@PostMapping("/add")
	public String saveV4(Item item)
	{
		itemRepository.save(item);
		return "basic/item";
	}
	
	@GetMapping("{itemId}/edit")
	public String edit(@PathVariable Long itemId, Item updateParam, Model model)
	{
		itemRepository.update(itemId, updateParam);
		Item getItem = itemRepository.findById(itemId);
		model.addAttribute("item", getItem);
		
		return "basic/item/";
	}
	
	/*
	 * 테스트용 데이터 추가
	 */
	@PostConstruct
	public void init()
	{
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
	
}
