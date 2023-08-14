package com.atticket.payment.controller;

import static com.atticket.common.response.BaseResponse.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.payment.dto.request.ConfirmReceiptReqDto;
import com.atticket.payment.dto.request.PostCancelPaymentReqDto;
import com.atticket.payment.dto.response.ConfirmReceiptResDto;
import com.atticket.payment.dto.response.PostCancelPaymentResDto;
import com.atticket.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

	private final PaymentService paymentService;

	/**
	 * 결제 확인
	 * @param reqDto
	 * @return
	 */
	@PostMapping("")
	public BaseResponse<ConfirmReceiptResDto> confirmReceipt(@RequestBody ConfirmReceiptReqDto reqDto) {

		log.debug("confirmReceipt - confirmReceiptReqDto : " + reqDto);
		return ok(paymentService.confirmReceipt(reqDto.getPaymentId()));
	}

	/**
	 * 결제 취소
	 * @param postCancelPaymentReqDto
	 * @return
	 */
	@PostMapping("/cancel")
	public BaseResponse<PostCancelPaymentResDto> postCancelPayment(
		@RequestBody PostCancelPaymentReqDto postCancelPaymentReqDto) {

		log.debug("postCancelPayment - postCancelPaymentReqDto : " + postCancelPaymentReqDto);
		return ok(paymentService.cancelPayment(postCancelPaymentReqDto));

	}

}
