package com.atticket.product.type;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;

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

	public static SubCategory findByName(String name) {
		if (!StringUtils.hasText(name)) {
			return null;
		}

		return Arrays.stream(SubCategory.values())
			.filter(subCategory -> subCategory.name().equals(name))
			.findAny().orElseThrow(() -> new BaseException(BaseStatus.INVALID_SUB_CATEGORY));
	}
}
