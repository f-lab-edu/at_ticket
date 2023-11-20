package com.atticket.product.client.client;

import com.atticket.common.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reservationClient", url = "${api.reservation.url}:${api.reservation.port}/reservations")
public interface ReservationFeignClient {
	@GetMapping(value = "/show/{showId}/seats")
	BaseResponse<List<Long>> getReservationSeatIds(@PathVariable("showId") Long showId);

	@GetMapping(value = "/show/{showId}/seats/pre")
	BaseResponse<List<Long>> getPreReservationSeatIds(@PathVariable("showId") Long showId);
}
