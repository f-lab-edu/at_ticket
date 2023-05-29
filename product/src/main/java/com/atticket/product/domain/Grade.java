package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Grade {

	//등급 id
	private Long id;

	//등급 타입
	private String type;

	//등급 가격
	private int price;

	//상품 id
	private Product product;
}
