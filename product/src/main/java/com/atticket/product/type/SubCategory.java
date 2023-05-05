package com.atticket.product.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubCategory {
	ORIGINAL("오리지널"),
	CREATIVE("창작"),
	HORROR("공포"),
	ROMANCE("로맨스"),
	COMEDY("코미디");

	private final String subCategory;
}
