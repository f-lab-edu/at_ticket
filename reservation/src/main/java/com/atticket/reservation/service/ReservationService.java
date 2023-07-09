package com.atticket.reservation.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.reservation.domain.Reservation;
import com.atticket.reservation.dto.service.RegisterReservationSvcDto;
import com.atticket.reservation.repository.ReservationRepository;
import com.atticket.reservation.type.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservedSeatService reservedSeatService;

	private final ReservationRepository reservationRepository;

	@Transactional
	public Long registerReservation(RegisterReservationSvcDto svcDto) {

		// 예약 좌석이 존재한다면
		if (reservedSeatService.existsReservedSeat(svcDto.getShowId(), svcDto.getSeatIds())) {
			throw new BaseException(BaseStatus.EXIST_RESERVED_SEAT);
		}
		reservedSeatService.saveReservedSeat(svcDto.getShowId(), svcDto.getSeatIds());

		Reservation reservation = Reservation.builder()
			.showId(svcDto.getShowId())
			.seatIds(svcDto.getSeatIds())
			.status(Status.WAIT_PAY)
			.time(LocalDateTime.now())
			.build();
		reservationRepository.save(reservation);

		return reservation.getId();
	}
}
