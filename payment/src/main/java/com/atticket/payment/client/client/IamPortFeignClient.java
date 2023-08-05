package com.atticket.payment.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.atticket.payment.client.dto.reponse.GetIamPortAccessTokenResDto;
import com.atticket.payment.client.dto.reponse.GetIamPortReceiptResDto;

import com.atticket.payment.client.dto.reponse.PostIamPortPaymentCancelResDto;
import com.atticket.payment.client.dto.request.GetIamPortAccessTokenReqDto;
import com.atticket.payment.client.dto.request.PostIamPortPaymentCancelReqDto;


@FeignClient(name = "reservationClient", url = "https://api.iamport.kr/", decode404 = true)
public interface IamPortFeignClient {

	@PostMapping(value = "/users/getToken")
	GetIamPortAccessTokenResDto getAccessToken(GetIamPortAccessTokenReqDto accessTokenReqDto);

	@GetMapping(value = "/payments/{impId}")
	GetIamPortReceiptResDto getReceipt(@RequestHeader("Authorization") String accessToken,
		@PathVariable("impId") String impId);


	@PostMapping(value = "/payments/cancel")
	PostIamPortPaymentCancelResDto postPaymentCancel(@RequestHeader("Authorization") String accessToken,
		PostIamPortPaymentCancelReqDto postIamPortPaymentCancelReqDto);
}
