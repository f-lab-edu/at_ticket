package com.atticket.product.client.client;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.client.dto.GetReservationSeatIdsResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservationClient", url = "${api.reservation.url}:${api.reservation.port}/reservations")
public interface ReservationFeignClient {
	@GetMapping(value = "/show/{showId}/seats")
	BaseResponse<GetReservationSeatIdsResDto> getReservationSeatIds(@PathVariable("showId") Long showId);

	@GetMapping(value = "/show/{showId}/seats/pre")
	BaseResponse<GetReservationSeatIdsResDto> getPreReservationSeatIds(@PathVariable("showId") Long showId);
}
