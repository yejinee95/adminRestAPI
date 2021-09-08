package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

	// HTML <Form> //ajax 검색
	// , produces = {"application-json"} 처럼 문법을 바꾼다
	@PostMapping("/postMethod" )
	public SearchParam postMethod(@RequestBody SearchParam searchParam){
		System.out.println(searchParam.getPage());
		return searchParam;
	}
}
