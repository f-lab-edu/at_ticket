package com.atticket.reservation.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.SampleDto;
import com.atticket.reservation.domain.ReservedSeat;
import com.atticket.reservation.dto.request.RegisterReservationReqDto;
import com.atticket.reservation.dto.response.GetReservationSeatsResDto;
import com.atticket.reservation.dto.response.RegisterReservationResDto;
import com.atticket.reservation.service.ReservationService;
import com.atticket.reservation.service.ReservedSeatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	private final ReservedSeatService reservedSeatService;

	/**
	 * 예약조회
	 * */
	@GetMapping("")
	public BaseResponse<SampleDto> getReservations() {
		return ok(SampleDto.builder().content("hi").build());
	}

	/**
	 * 예약하기
	 */
	@PostMapping("")
	public BaseResponse<RegisterReservationResDto> registerReservation(
		@Valid @RequestBody RegisterReservationReqDto reqDto) {

		Long reservationId = reservationService.registerReservation(reqDto.convert());
		return ok(RegisterReservationResDto.construct(reservationId));
	}

	/**
	 * 예약된 좌석 리스트 조회
	 * @param showId
	 * @return
	 */
	@GetMapping("/show/{showId}/seats")
	public BaseResponse<GetReservationSeatsResDto> getReservationSeats(@PathVariable("showId") Long showId) {

		List<ReservedSeat> seats = reservedSeatService.getReservedSeatsByShowId(showId);

		return ok(
			GetReservationSeatsResDto.construct(seats)
		);
	}
}
