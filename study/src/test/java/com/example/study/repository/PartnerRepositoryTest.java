package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Partner;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class PartnerRepositoryTest extends StudyApplicationTests {

	@Autowired
	private PartnerRepository partnerRepository;

	@Test
	public void create(){
		String name = "롯데";
		String status = "REGISTERED";
		String address = "서초꽃마을 1502";
		String callCenter = "070-1111-1111";
		String partnerNumber = "010-1111-2222";
		String businessNumber = "080-1111-2222";
		String ceoName = "신동빈";
		LocalDateTime registeredAt = LocalDateTime.now();
		LocalDateTime createdAt = LocalDateTime.now();
		String createdBy = "AdminServer";
		Long categoryId = 1L;

		Partner partner = new Partner();
		partner.setName(name);
		partner.setStatus(status);
		partner.setAddress(address);
		partner.setCallCenter(callCenter);
		partner.setPartnerNumber(partnerNumber);
		partner.setBusinessNumber(businessNumber);
		partner.setCeoName(ceoName);
		partner.setRegisteredAt(registeredAt);
		partner.setCreatedAt(createdAt);
		partner.setCreatedBy(createdBy);
//		partner.setCategory();

		Partner newPartner = partnerRepository.save(partner);
		Assertions.assertNotNull(newPartner);
		Assertions.assertEquals(newPartner.getName(),name);
		Assertions.assertEquals(newPartner.getCeoName(),ceoName);
	}

	@Test
	public void read(){
		String businessNumber = "080-1111-2222";
		Partner partner = partnerRepository.findFirstByBusinessNumberOrderByIdDesc(businessNumber);

		Assertions.assertNotNull(partner);
	}
}
