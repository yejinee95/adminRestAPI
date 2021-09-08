package com.example.study.controller.api;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.service.PartnerApiLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/partner")
public class PartnerApiController implements CrudInterFace<PartnerApiRequest, PartnerApiResponse> {

	@Autowired
	private PartnerApiLoginService partnerApiLoginService;

	@Override
	@PostMapping("")
	public Header<PartnerApiResponse> create(@RequestBody Header<PartnerApiRequest> request) {
		return partnerApiLoginService.create(request);
	}

	@Override
	@GetMapping("{id}")
	public Header<PartnerApiResponse> read(@PathVariable Long id) {
		return partnerApiLoginService.read(id);
	}

	@Override
	@PutMapping("")
	public Header<PartnerApiResponse> update(@RequestBody Header<PartnerApiRequest> request) {
		return partnerApiLoginService.update(request);
	}

	@Override
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		return partnerApiLoginService.delete(id);
	}
}
