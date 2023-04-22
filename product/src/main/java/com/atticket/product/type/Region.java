package com.atticket.product.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Region {

	SEOUL("서울"),
	DEAJOEN("대전"),
	GWANJU("광주");

	private final String region;
}
