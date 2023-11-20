package com.atticket.reservation.service;

import com.atticket.common.jwtmanager.JwtManager;
import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.BaseStatus;
import com.atticket.reservation.client.client.PaymentFeignClient;
import com.atticket.reservation.client.client.ProductFeignClient;
import com.atticket.reservation.client.dto.request.ConfirmReceiptReqDto;
import com.atticket.reservation.client.dto.request.GetSeatsInfoReqDto;
import com.atticket.reservation.client.dto.response.ConfirmReceiptResDto;
import com.atticket.reservation.domain.Reservation;
import com.atticket.reservation.repository.ReservationRepository;
import com.atticket.reservation.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservedSeatService reservedSeatService;
	private final PreReservedSeatService preReservedSeatService;
	private final ReservationRepository reservationRepository;

	private final ProductFeignClient productFeignClient;
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
	public Long registerReservation(String paymentId, Long reservationId, String userId) {

		Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, userId).orElse(null);

		// 예약내역이 조회되지 않으면 exception
		if (Objects.isNull(reservation)) {
			throw new BaseException(BaseStatus.INVALID_RESERVATION);
		}

		// 예약이 이미 완료된 상태라면 exception
		if (Status.RESERVED.equals(reservation.getStatus())) {
			throw new BaseException(BaseStatus.ALREADY_RESERVED);
		}

		BaseResponse<Integer> priceRes = productFeignClient.getSeatsPrice(reservation.getShowId(),
			GetSeatsInfoReqDto.builder().seatIds(reservation.getSeatIds()).build());

		// 영수증 조회
		BaseResponse<ConfirmReceiptResDto> paymentRes = paymentFeignClient.confirmReceipt(
			ConfirmReceiptReqDto.builder()
				.paymentId(paymentId)
				.reservationId(reservationId)
				.amount(priceRes.getData())
				.build());

		// 영수증이 유효하지 않으면 exception
		if (Objects.isNull(paymentRes.getData()) || Objects.isNull(paymentRes.getData().getPaymentId())) {
			throw new BaseException(BaseStatus.INVALID_PAY_INFO);
		}

		Long showId = reservation.getShowId();
		List<Long> seatIds = reservation.getSeatIds();
		// 예약 좌석 등록
		reservedSeatService.registerReservedSeat(showId, seatIds);
		// 선예약 좌석 삭제
		preReservedSeatService.deletePreReservedSeat(showId, seatIds);

		reservation.reserve();
		return reservation.getId();
	}

	// 예약 조회하기
	public Reservation getReservation(Long reservationId, String userId) {

		return reservationRepository.findByIdAndUserId(reservationId, userId).orElse(null);
	}
}
