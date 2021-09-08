package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderGroupLoginService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse,OrderGroup> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

		OrderGroupApiRequest body = request.getData();

		OrderGroup orderGroup = OrderGroup.builder()
						.status(body.getStatus())
						.orderAt(LocalDateTime.now())
						.orderType(body.getOrderType())
						.revAddress(body.getRevAddress())
						.revName(body.getRevName())
						.paymentType(body.getPaymentType())
						.totalPrice(body.getTotalPrice())
						.totalQuantity(body.getTotalQuantity())
						.arrivalDate(body.getArrivalDate())
						.user(userRepository.getOne(body.getUserId()))
						.build();

		OrderGroup newOrderGroup = baseRepository.save(orderGroup);
		return response(newOrderGroup);
	}

	@Override
	public Header<OrderGroupApiResponse> read(Long id) {
		return baseRepository.findById(id)
						.map(orderGroup -> response(orderGroup))
						.orElseGet(()-> Header.ERROR("데이터없음"));
	}

	@Override
	public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {

		OrderGroupApiRequest orderGroupApiRequest = request.getData();


		return	baseRepository.findById(orderGroupApiRequest.getId())
						.map(entityOrderGroup -> {
							entityOrderGroup.setStatus(orderGroupApiRequest.getStatus())
											.setOrderAt(orderGroupApiRequest.getOrderAt())
											.setOrderType(orderGroupApiRequest.getOrderType())
											.setRevAddress(orderGroupApiRequest.getRevAddress())
											.setRevName(orderGroupApiRequest.getRevName())
											.setPaymentType(orderGroupApiRequest.getPaymentType())
											.setTotalPrice(orderGroupApiRequest.getTotalPrice())
											.setTotalQuantity(orderGroupApiRequest.getTotalQuantity())
											.setArrivalDate(orderGroupApiRequest.getArrivalDate())
							;
							return entityOrderGroup;
						})
						.map(newEntityOrderGroup -> baseRepository.save(newEntityOrderGroup))
						.map(updateOrderGroup -> response(updateOrderGroup))
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	@Override
	public Header delete(Long id) {
		return	baseRepository.findById(id)
						.map(orderGroup -> {
							baseRepository.delete(orderGroup);
							return Header.OK();
						})
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	public Header<OrderGroupApiResponse> response(OrderGroup orderGroup){
		OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
						.id(orderGroup.getId())
						.status(orderGroup.getStatus())
						.arrivalDate(orderGroup.getArrivalDate())
						.orderAt(orderGroup.getOrderAt())
						.orderType(orderGroup.getOrderType())
						.paymentType(orderGroup.getPaymentType())
						.revAddress(orderGroup.getRevAddress())
						.revName(orderGroup.getRevName())
						.totalPrice(orderGroup.getTotalPrice())
						.totalQuantity(orderGroup.getTotalQuantity())
						.userId(orderGroup.getUser().getId())
						.build();

		// Header + data

		return Header.OK(orderGroupApiResponse);
	}
}
