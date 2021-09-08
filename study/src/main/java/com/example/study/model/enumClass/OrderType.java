package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

	ALL(3,"묶음","묶음배송"),
	EACH(4,"개별","개별배송");

	private Integer id;
	private String title;
	private String description;
}
