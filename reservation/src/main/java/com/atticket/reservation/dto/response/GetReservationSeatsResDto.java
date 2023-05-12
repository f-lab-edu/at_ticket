package com.atticket.reservation.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;

import com.atticket.reservation.domain.ReservedSeat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetReservationSeatsResDto {

	//예약된 좌석 리스트
	private final List<ReservedSeatsDto> reservedSeats;

	public static GetReservationSeatsResDto construct(List<ReservedSeat> reservedSeats) {
		if (ObjectUtils.isEmpty(reservedSeats)) {
			return null;
		}
		return new GetReservationSeatsResDto(
			reservedSeats.stream().map(ReservedSeatsDto::construct).collect(Collectors.toList()));

	}

	@Getter
	@Builder
	private static class ReservedSeatsDto {

		//예약좌석 id
		private final Long id;

		//공연 id
		private final Long showId;

		//좌석 id
		private final Long seatId;

		private static ReservedSeatsDto construct(ReservedSeat reservedSeat) {
			return ReservedSeatsDto.builder()
				.id(reservedSeat.getSeatId())
				.showId(reservedSeat.getShowId())
				.seatId(reservedSeat.getSeatId())
				.build();
		}
	}

}
