package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderDetailApiLogicService implements CrudInterFace<OrderDetailApiRequest, OrderDetailApiResponse> {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderGroupRepository orderGroupRepository;

	@Override
	public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {

		OrderDetailApiRequest body = request.getData();

		OrderDetail orderDetail = OrderDetail.builder()
						.status(body.getStatus())
						.arrivalDate(LocalDateTime.now().plusDays(2))
						.quantity(body.getQuantity())
						.totalPrice(body.getTotalPrice())
						.orderGroup(orderGroupRepository.getById(body.getOrderGroupId()))
						.item(itemRepository.getById(body.getItemId()))
						.build();

		OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
		return response(newOrderDetail);
	}

	@Override
	public Header<OrderDetailApiResponse> read(Long id) {

		// id -> repository getOne , getById
		Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(id);
		System.out.println("시작");
		System.out.println(optionalOrderDetail);
		// user -> userAPiResponse return
		return optionalOrderDetail
						.map(orderDetail -> response(orderDetail))
						.orElseGet(()-> Header.ERROR("데이터없음"));

	}

	@Override
	public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
		OrderDetailApiRequest orderDetailApiRequest = request.getData();

		return	orderDetailRepository.findById(orderDetailApiRequest.getId())
						.map(entityOrderDetail -> {
							entityOrderDetail.setStatus(orderDetailApiRequest.getStatus())
											.setArrivalDate(orderDetailApiRequest.getArrivalDate())
											.setQuantity(orderDetailApiRequest.getQuantity())
											.setTotalPrice(orderDetailApiRequest.getTotalPrice())
							;
							return entityOrderDetail;
						})
						.map(newEntityOrderDetail -> orderDetailRepository.save(newEntityOrderDetail))
						.map(updateOrderDetail -> response(updateOrderDetail))
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	@Override
	public Header delete(Long id) {
		return	orderDetailRepository.findById(id)
						.map(orderGroup -> {
							orderDetailRepository.delete(orderGroup);
							return Header.OK();
						})
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	private Header<OrderDetailApiResponse> response(OrderDetail orderDetail) {
		OrderDetailApiResponse orderDetailApiResponse = OrderDetailApiResponse.builder()
						.id(orderDetail.getId())
						.status(orderDetail.getStatus())
						.arrivalDate(orderDetail.getArrivalDate())
						.quantity(orderDetail.getQuantity())
						.totalPrice(orderDetail.getTotalPrice())
						.build();

		// Header + data

		return Header.OK(orderDetailApiResponse);
	}


}
