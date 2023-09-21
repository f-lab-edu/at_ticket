package com.atticket.product.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.client.dto.GetReservationSeatsResDto;

@FeignClient(name = "reservationClient", url = "${api.reservation.url}:${api.reservation.port}/reservations")
public interface ReservationFeignClient {
	@GetMapping(value = "/show/{showId}/seats")
	BaseResponse<GetReservationSeatsResDto> getReservationSeats(@PathVariable("showId") Long showId);

	@GetMapping(value = "/show/{showId}/seats/pre")
	BaseResponse<GetReservationSeatsResDto> getPreReservationSeats(@PathVariable("showId") Long showId);
}
