package com.atticket.product.service;

import com.atticket.product.client.client.ReservationFeignClient;
import com.atticket.product.client.dto.GetReservationSeatIdsResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservedSeatService {

	private final ReservationFeignClient reservationFeignClient;

	/**
	 * 공연의 예매된 좌석 id 리스트 조회
	 *
	 * @param showId
	 * @return
	 */
	public List<Long> getReservedSeatIdsByShowId(Long showId) {
		List<Long> result = new ArrayList<>();

		// 예약 좌석
		GetReservationSeatIdsResDto reservedSeatRes = reservationFeignClient.getReservationSeatIds(showId).getData();

		if (!Objects.isNull(reservedSeatRes)) {
			result.addAll(reservedSeatRes.getSeatIds());
		}

		// 선예약 좌석
		GetReservationSeatIdsResDto preReservedSeatRes = reservationFeignClient.getPreReservationSeatIds(showId).getData();

		if (!Objects.isNull(preReservedSeatRes)) {
			result.addAll(preReservedSeatRes.getSeatIds());
		}

		return result.stream().distinct().collect(Collectors.toList());
	}

}
