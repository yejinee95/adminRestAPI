package com.example.study.model.network.request;

import com.example.study.model.enumClass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemAPiRequest {

	private Long id;

	private UserStatus status;

	private String name;

	private String title;

	private String content;

	private BigDecimal price;

	private String brandName;

	private LocalDateTime registeredAt;

	private LocalDateTime unregisteredAt;

	private Long partnerId;
}
