package com.atticket.reservation.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.jwtmanager.JwtManager;
import com.atticket.common.response.BaseResponse;
import com.atticket.reservation.domain.PreReservedSeat;
import com.atticket.reservation.domain.ReservedSeat;
import com.atticket.reservation.dto.request.PreRegisterReservationReqDto;
import com.atticket.reservation.dto.request.RegisterReservationReqDto;
import com.atticket.reservation.dto.response.GetReservationResDto;
import com.atticket.reservation.dto.response.GetReservationSeatIdsResDto;
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
	 * 예약 조회
	 */
	@Cacheable(value = "getReservation", key = "#reservationId")
	@GetMapping("/{reservationId}")
	public BaseResponse<GetReservationResDto> getReservation(@Valid @PathVariable("reservationId") Long reservationId) {

		// 유저 토큰 조회
		String userId = JwtManager.getUserInfo().getUserId();

		return ok(GetReservationResDto.construct(reservationService.getReservation(reservationId, userId)));

	}

	/**
	 * 선예약하기
	 */
	@PostMapping("/pre")
	public BaseResponse<RegisterReservationResDto> preRegisterReservation(
		@Valid @RequestBody PreRegisterReservationReqDto reqDto) throws ExecutionException, InterruptedException {

		// 유저 토큰 조회
		String userId = JwtManager.getUserInfo().getUserId();
		reservationService.preRegisterReservation(reqDto.getShowId(), reqDto.getSeatIds(), userId);
		return ok();
	}

	/**
	 * 예약하기
	 */
	@PostMapping("")
	public BaseResponse<RegisterReservationResDto> registerReservation(
		@Valid @RequestBody RegisterReservationReqDto reqDto) {

		// 유저 토큰 조회
		String userId = JwtManager.getUserInfo().getUserId();

		Long reservationId = reservationService.registerReservation(reqDto.getPaymentId(), reqDto.getReservationId(),
			userId);
		return ok(RegisterReservationResDto.construct(reservationId));
	}

	/**
	 * 예약된 좌석 id 리스트 조회
	 *
	 * @param showId
	 * @return
	 */

	@GetMapping("/show/{showId}/seats")
	public BaseResponse<GetReservationSeatIdsResDto> getReservationSeatIds(@PathVariable("showId") Long showId) {

		List<ReservedSeat> seats = reservedSeatService.getReservedSeatsByShowId(showId);
		List<Long> seatIds = seats.stream().map(seat -> seat.getSeatId()).collect(Collectors.toList());

		return ok(GetReservationSeatIdsResDto.construct(seatIds));
	}

	/**
	 * 선예약 좌석 id 리스트 조회
	 */

	@GetMapping("/show/{showId}/seats/pre")
	public BaseResponse<GetReservationSeatIdsResDto> getPreReservationSeatIds(@PathVariable("showId") Long showId) {

		List<PreReservedSeat> seats = reservedSeatService.getPreReservedSeatsByShowId(showId);
		List<Long> seatIds = seats.stream().map(seat -> seat.getSeatId()).collect(Collectors.toList());

		return ok(GetReservationSeatIdsResDto.construct(seatIds));
	}
}
