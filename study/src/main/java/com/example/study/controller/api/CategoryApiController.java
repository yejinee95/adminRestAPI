package com.example.study.controller.api;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import com.example.study.service.CategoryApiLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryApiController implements CrudInterFace<CategoryApiRequest, CategoryApiResponse> {

	@Autowired
	private CategoryApiLoginService categoryApiLoginService;

	@Override
	@PostMapping("")
	public Header<CategoryApiResponse> create(@RequestBody Header<CategoryApiRequest> request) {
		return categoryApiLoginService.create(request);
	}

	@Override
	@GetMapping("{id}")
	public Header<CategoryApiResponse> read(@PathVariable Long id) {
		return categoryApiLoginService.read(id);
	}

	@Override
	@PutMapping("")
	public Header<CategoryApiResponse> update(@RequestBody Header<CategoryApiRequest> request) {
		return categoryApiLoginService.update(request);
	}

	@Override
	@DeleteMapping("{id}")
	public Header delete(@PathVariable Long id) {
		return categoryApiLoginService.delete(id);
	}
}
