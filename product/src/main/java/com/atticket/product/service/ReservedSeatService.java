package com.atticket.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.client.client.ReservationFeignClient;
import com.atticket.product.client.dto.GetReservationSeatsResDto;

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
		List<Long> result = new ArrayList<>();

		// 예약 좌석
		GetReservationSeatsResDto reservedSeatsRes = reservationFeignClient.getReservationSeats(showId).getData();

		if (!Objects.isNull(reservedSeatsRes)) {
			result.addAll(reservedSeatsRes.getReservedSeats().stream()
				.map(seat -> seat.getSeatId())
				.collect(Collectors.toList()));
		}

		// 선예약 좌석
		GetReservationSeatsResDto preReservedSeatsRes = reservationFeignClient.getPreReservationSeats(showId).getData();

		if (!Objects.isNull(preReservedSeatsRes)) {
			result.addAll(preReservedSeatsRes.getReservedSeats().stream()
				.map(seat -> seat.getSeatId())
				.collect(Collectors.toList()));
		}

		return result.stream().distinct().collect(Collectors.toList());
	}

}
