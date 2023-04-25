package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Grade {

	private Long id;

	private String type;

	private int price;

	private Long productId;
}
