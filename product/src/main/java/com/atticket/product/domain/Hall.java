package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Hall {

	//공연홀 Id
	private Long id;

	//이름
	private String name;

	//공연장 Id
	private Long placeId;
}
