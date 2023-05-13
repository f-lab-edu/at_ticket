package com.atticket.product.type;

import static org.springframework.util.StringUtils.hasText;

import java.util.Arrays;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;

public enum SortOption {
	NAME,
	RECENT,
	END_DATE;

	public static SortOption findByName(String name) {
		if (!hasText(name)) {
			return null;
		}

		return Arrays.stream(SortOption.values())
			.filter(SortOption -> SortOption.name().equals(name))
			.findAny().orElseThrow(() -> new BaseException(BaseStatus.INVALID_SORT_OPT));
	}
}
