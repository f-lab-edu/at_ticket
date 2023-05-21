package com.atticket.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.client.client.ReservationFeignClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservedSeatService {

	private final ReservationFeignClient reservationFeignClient;

	/**
	 * 공연의 예매된 좌석 id 리스트 조회
	 * @param showId
	 * @return
	 */
	public List<Long> getReservedSeatIdsByShowId(Long showId) {
		if (Objects.isNull(reservationFeignClient.getReservationSeats(showId).getData())) {
			return new ArrayList<>();
		}

		return reservationFeignClient.getReservationSeats(showId)
			.getData().getReservedSeats().stream()
			.map(seat -> seat.getSeatId())
			.collect(Collectors.toList());
	}

}
