package com.atticket.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.client.client.ReservationFeignClient;
import com.atticket.product.client.dto.GetReservationSeatsResDto;
import com.atticket.product.domain.ReservedSeat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservedSeatService {

	private final ReservationFeignClient reservationFeignClient;

	/**
	 * 공연의 예매된 좌석 리스트 조회
	 * @param showId
	 * @return
	 */
	public List<ReservedSeat> getReservedSeatsByShowId(Long showId) {

		GetReservationSeatsResDto reservationSeats =
			reservationFeignClient.getReservationSeats(String.valueOf(showId)).getData();

		List<ReservedSeat> reservedSeats = reservationSeats.getReservedSeats().stream()
			.map(
				seat ->
					ReservedSeat.builder()
						.id(seat.getId())
						.seatId(seat.getSeatId())
						.showId(seat.getShowId())
						.build()
			).collect(Collectors.toList());

		return reservedSeats;
	}

}
