package com.example.study.controller.api;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminUserApiRequest;
import com.example.study.model.network.response.AdminUserApiResponse;
import com.example.study.service.AdminUserApiLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/adminUser")
public class AdminUserApiController implements CrudInterFace<AdminUserApiRequest, AdminUserApiResponse> {

	@Autowired
	private AdminUserApiLoginService adminUserApiLoginService;

	@Override
	@PostMapping("")
	public Header<AdminUserApiResponse> create(@RequestBody Header<AdminUserApiRequest> request) {
		return adminUserApiLoginService.create(request);
	}

	@Override
	@GetMapping("{id}")
	public Header<AdminUserApiResponse> read(@PathVariable Long id) {
		return adminUserApiLoginService.read(id);
	}

	@Override
	@PutMapping("")
	public Header<AdminUserApiResponse> update(@RequestBody Header<AdminUserApiRequest> request) {
		return adminUserApiLoginService.update(request);
	}

	@Override
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		return adminUserApiLoginService.delete(id);
	}
}
