package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

	@Autowired
	private ItemRepository itemRepository;

	@Test
	public void create(){
		Item item = new Item();
	//	item.setStatus("UNREGISTERED");
		item.setName("삼성 노트북");
		item.setTitle("삼성 노트북 a100");
		item.setContent("2019년형 노트북");
		//item.setPrice(900000);
		item.setBrandName("삼성");
		item.setRegisteredAt(LocalDateTime.now());
		item.setCreatedAt(LocalDateTime.now());
		item.setCreatedBy("롯데");
//		item.setPartner();

		Item newItem = itemRepository.save(item);
		Assertions.assertNotNull(newItem);
		Assertions.assertEquals(newItem.getName(),"삼성 노트북");
	}

	@Test
	@Transactional
	public void read(){
//		Long id = 1L;
//		Optional<Item> item = itemRepository.findById(id);
//
//		Assertions.assertTrue(item.isPresent());
//
//		item.ifPresentOrElse(selectItem ->
//										selectItem.getOrderDetailList().stream().forEach(detail -> {
//											User user = detail.getUser();
//											System.out.println(user);
//										}),
//						() -> System.out.println("정보가 없습니다" )
//		);

	}
}
