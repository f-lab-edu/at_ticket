package com.atticket.product.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Region {

	SEOUL("서울"),
	DAEJEON("대전"),
	GWANGJU("광주");

	private final String region;
}
