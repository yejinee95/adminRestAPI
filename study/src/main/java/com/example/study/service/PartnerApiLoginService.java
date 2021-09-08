package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.entity.Category;
import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerApiLoginService implements CrudInterFace<PartnerApiRequest, PartnerApiResponse> {

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
		PartnerApiRequest body = request.getData();

		Partner partner = Partner.builder()
						.address(body.getAddress())
						.businessNumber(body.getBusinessNumber())
						.callCenter(body.getCallCenter())
						.ceoName(body.getCeoName())
						.name(body.getName())
						.partnerNumber(body.getPartnerNumber())
						.status(body.getStatus())
						.registeredAt(body.getRegisteredAt())
						.unregisteredAt(body.getUnregisteredAt())
						.category(categoryRepository.getOne(body.getCategoryId()))
						.build();
		Partner newPartner = partnerRepository.save(partner);
		return response(newPartner);
	}

	@Override
	public Header<PartnerApiResponse> read(Long id) {
		return partnerRepository.findById(id)
						.map(partner -> response(partner))
						.orElseGet(()-> Header.ERROR("데이터없음"));
	}

	@Override
	public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {

		PartnerApiRequest partnerApiRequest = request.getData();

		return partnerRepository.findById(partnerApiRequest.getId())
						.map(entityPartner -> {
							entityPartner.setStatus(partnerApiRequest.getStatus())
											.setName(partnerApiRequest.getName())
											.setAddress(partnerApiRequest.getAddress())
											.setCallCenter(partnerApiRequest.getCallCenter())
											.setPartnerNumber(partnerApiRequest.getPartnerNumber())
											.setBusinessNumber(partnerApiRequest.getBusinessNumber())
											.setCeoName(partnerApiRequest.getCeoName())
											.setRegisteredAt(partnerApiRequest.getRegisteredAt())
											.setUnregisteredAt(partnerApiRequest.getUnregisteredAt())
											;
							return entityPartner;
						})
						.map(newEntityPartner -> partnerRepository.save(newEntityPartner))
						.map(updatePartner -> response(updatePartner))
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	@Override
	public Header delete(Long id) {
		return	partnerRepository.findById(id)
						.map(partner -> {
							partnerRepository.delete(partner);
							return Header.OK();
						})
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	private Header<PartnerApiResponse> response(Partner partner){
		PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
						.id(partner.getId())
						.address(partner.getAddress())
						.businessNumber(partner.getBusinessNumber())
						.callCenter(partner.getCallCenter())
						.registeredAt(partner.getRegisteredAt())
						.unregisteredAt(partner.getUnregisteredAt())
						.name(partner.getName())
						.ceoName(partner.getCeoName())
						.status(partner.getStatus())
						.partnerNumber(partner.getPartnerNumber())
						.categoryId(partner.getCategory().getId())
						.build();

		// Header + data

		return Header.OK(partnerApiResponse);
	}
}
