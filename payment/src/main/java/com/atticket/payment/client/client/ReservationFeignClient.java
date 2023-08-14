package com.atticket.payment.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.atticket.common.response.BaseResponse;
import com.atticket.payment.client.dto.reponse.GetReservationRes;

@FeignClient(name = "reservationClient", url = "${api.reservation.url}:${api.reservation.port}/reservations")
public interface ReservationFeignClient {

	@GetMapping(value = "/{reservationId}")
	BaseResponse<GetReservationRes> getReservation(@RequestHeader("Authorization") String userToken,
		@PathVariable("reservationId") Long id);

}
