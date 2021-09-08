package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.entity.Item;
import com.example.study.model.enumClass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemAPiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemApiLogicService extends BaseService<ItemAPiRequest, ItemApiResponse, Item> {

	@Autowired
	private PartnerRepository partnerRepository;

	@Override
	public Header<ItemApiResponse> create(Header<ItemAPiRequest> request) {

		ItemAPiRequest body = request.getData();

		Item item = Item.builder()
						.status(body.getStatus())
						.name(body.getName())
						.title(body.getTitle())
						.content(body.getContent())
						.price(body.getPrice())
						.brandName(body.getBrandName())
						.registeredAt(LocalDateTime.now())
						.partner(partnerRepository.getOne(body.getPartnerId()))
						.build();

		Item newItem = baseRepository.save(item);
		return response(newItem);
	}

	@Override
	public Header<ItemApiResponse> read(Long id) {
		return baseRepository.findById(id)
						.map(item -> response(item))
						.orElseGet(()-> Header.ERROR("데이터없음"));
	}

	@Override
	public Header<ItemApiResponse> update(Header<ItemAPiRequest> request) {

		ItemAPiRequest itemAPiRequest = request.getData();

		return	baseRepository.findById(itemAPiRequest.getId())
						.map(entityItem -> {
							entityItem.setStatus(itemAPiRequest.getStatus())
											.setTitle(itemAPiRequest.getTitle())
											.setName(itemAPiRequest.getName())
											.setContent(itemAPiRequest.getContent())
											.setPrice(itemAPiRequest.getPrice())
											.setBrandName(itemAPiRequest.getBrandName())
											.setRegisteredAt(itemAPiRequest.getRegisteredAt())
											.setUnregisteredAt(itemAPiRequest.getUnregisteredAt())
											;
							return entityItem;
						})
						.map(newEntityItem -> baseRepository.save(newEntityItem))
						.map(updateItem -> response(updateItem))
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	@Override
	public Header delete(Long id) {
		return	baseRepository.findById(id)
						.map(item -> {
							baseRepository.delete(item);
							return Header.OK();
						})
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	public Header<ItemApiResponse> response(Item item){

		ItemApiResponse itemApiResponse = ItemApiResponse.builder()
						.id(item.getId())
						.name(item.getName())
						.status(item.getStatus())
						.price(item.getPrice())
						.title(item.getTitle())
						.content(item.getContent())
						.brandName(item.getBrandName())
						.registeredAt(item.getRegisteredAt())
						.unregisteredAt(item.getUnregisteredAt())
						.partnerId(item.getPartner().getId())
						.build();

		// Header + data

		return Header.OK(itemApiResponse);
	}
}
