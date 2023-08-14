package com.atticket.reservation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.BaseStatus;
import com.atticket.reservation.client.client.PaymentFeignClient;
import com.atticket.reservation.client.dto.request.ConfirmReceiptReqDto;
import com.atticket.reservation.client.dto.response.ConfirmReceiptResDto;
import com.atticket.reservation.domain.Reservation;
import com.atticket.reservation.repository.PreReservedSeatRepository;
import com.atticket.reservation.repository.ReservationRepository;
import com.atticket.reservation.type.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservedSeatService reservedSeatService;
	private final PreReservedSeatService preReservedSeatService;
	private final ReservationRepository reservationRepository;
	private final PreReservedSeatRepository preReservedSeatRepository;

	private final PaymentFeignClient paymentFeignClient;

	@Transactional
	public Long preRegisterReservation(Long showId, List<Long> seatIds, String userId) {
		preReservedSeatService.registerPreReservedSeat(showId, seatIds, userId);
		Reservation reservation = Reservation.builder()
			.showId(showId)
			.seatIds(seatIds)
			.userId(userId)
			.status(Status.WAIT_PAY)
			.time(LocalDateTime.now())
			.build();
		reservationRepository.save(reservation);

		return reservation.getId();
	}

	@Transactional
	public Long registerReservation(String paymentId, Long showId, List<Long> seatIds, String userId) {

		// 예약 좌석이 존재한다면
		if (reservedSeatService.existsReservedSeat(showId, seatIds)
			|| preReservedSeatRepository.existsByShowIdAndSeatIdInAndUserIdNot(showId, seatIds, userId)) {
			throw new BaseException(BaseStatus.EXIST_RESERVED_SEAT);
		}

		BaseResponse<ConfirmReceiptResDto> paymentRes = paymentFeignClient.confirmReceipt(
			ConfirmReceiptReqDto.builder().paymentId(paymentId).build());

		// 영수증이 유효하지 않으면 exception
		if (Objects.isNull(paymentRes.getData().getPaymentId())) {
			throw new BaseException(BaseStatus.INVALID_PAY_INFO);
		}

		// 예약 좌석 등록
		reservedSeatService.registerReservedSeat(showId, seatIds);
		// 선예약 좌석 삭제
		preReservedSeatService.deletePreReservedSeat(showId, seatIds);

		Reservation reservation = Reservation.builder()
			.showId(showId)
			.seatIds(seatIds)
			.userId(userId)
			.status(Status.RESERVED)
			.time(LocalDateTime.now())
			.build();
		reservationRepository.save(reservation);

		return reservation.getId();
	}

	// 예약 조회하기
	public Reservation getReservation(Long reservationId, String userId) {

		return reservationRepository.findByIdAndUserId(reservationId, userId).orElse(null);
	}
}
