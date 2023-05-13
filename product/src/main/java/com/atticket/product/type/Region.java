package com.atticket.product.type;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Region {

	SEOUL("서울"),
	DAEJEON("대전"),
	GWANGJU("광주");

	private final String region;

	public static Region findByName(String name) {
		if (!StringUtils.hasText(name)) {
			return null;
		}

		return Arrays.stream(Region.values())
			.filter(region -> region.name().equals(name))
			.findAny().orElseThrow(() -> new BaseException(BaseStatus.INVALID_REGION));
	}
}
