package com.atticket.reservation.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;

import com.atticket.reservation.domain.PreReservedSeat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPreReservationSeatsResDto implements Serializable {

	//예약된 좌석 리스트
	private final List<ReservedSeatsDto> reservedSeats;

	public static GetPreReservationSeatsResDto construct(List<PreReservedSeat> reservedSeats) {
		if (ObjectUtils.isEmpty(reservedSeats)) {
			return null;
		}
		return new GetPreReservationSeatsResDto(
			reservedSeats.stream().map(ReservedSeatsDto::construct).collect(Collectors.toList()));

	}

	@Getter
	@Builder
	private static class ReservedSeatsDto implements Serializable {

		//예약좌석 id
		private final Long id;

		//공연 id
		private final Long showId;

		//좌석 id
		private final Long seatId;

		private static ReservedSeatsDto construct(PreReservedSeat reservedSeat) {
			return ReservedSeatsDto.builder()
				.id(reservedSeat.getSeatId())
				.showId(reservedSeat.getShowId())
				.seatId(reservedSeat.getSeatId())
				.build();
		}
	}

}
