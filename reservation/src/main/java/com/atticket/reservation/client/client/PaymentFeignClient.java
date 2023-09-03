package com.atticket.reservation.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.atticket.common.response.BaseResponse;
import com.atticket.reservation.client.dto.request.ConfirmReceiptReqDto;
import com.atticket.reservation.client.dto.response.ConfirmReceiptResDto;

@FeignClient(name = "paymentClient", url = "${api.payment.url}:${api.payment.port}/payments")
public interface PaymentFeignClient {

	@PostMapping("")
	BaseResponse<ConfirmReceiptResDto> confirmReceipt(@RequestBody ConfirmReceiptReqDto reqDto);

}
