package com.atticket.payment.client.client;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.atticket.common.response.BaseResponse;
import com.atticket.payment.client.dto.request.GetSeatsInfoReqDto;

@FeignClient(name = "productClient", url = "${api.product.url}:${api.product.port}")
public interface ProductFeignClient {

	@PostMapping("/shows/{showId}/seats/price")
	BaseResponse<Integer> getSeatsPrice(@PathVariable("showId") Long showId,
		@Valid @RequestBody GetSeatsInfoReqDto reqDto);

}
