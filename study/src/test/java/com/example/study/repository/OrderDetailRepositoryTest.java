package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderDetailRepositoryTest extends StudyApplicationTests {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Test
	public void create(){

		OrderDetail orderDetail = new OrderDetail();

		//orderDetail.setOrderGroup()
		//orderDetail.setItemId(1L);
		orderDetail.setStatus("WAITING");
		orderDetail.setArrivalDate(LocalDateTime.now().plusDays(2));
		orderDetail.setQuantity(1);
		orderDetail.setTotalPrice(BigDecimal.valueOf(9000000));
		orderDetail.setCreatedAt(LocalDateTime.now());
		orderDetail.setCreatedBy("AdminServer");


		OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
		Assertions.assertNotNull(newOrderDetail);
	}

}
