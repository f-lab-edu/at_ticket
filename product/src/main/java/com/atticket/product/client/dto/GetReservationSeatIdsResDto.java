package com.atticket.product.client.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetReservationSeatIdsResDto {

	//예약 조회
	private final List<Long> seatIds;

	public static GetReservationSeatIdsResDto construct(List<Long> seatIds) {
		if (CollectionUtils.isEmpty(seatIds)) {
			return null;
		}
		return new GetReservationSeatIdsResDto(seatIds);

	}
}
