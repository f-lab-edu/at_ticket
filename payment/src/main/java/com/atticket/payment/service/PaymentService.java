package com.atticket.payment.service;

import static com.atticket.common.response.BaseStatus.INVALID_RECEIPT;
import static com.atticket.common.response.BaseStatus.INVALID_TOKEN;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atticket.common.response.BaseException;
import com.atticket.payment.Repository.PaymentRepository;
import com.atticket.payment.client.client.IamPortFeignClient;
import com.atticket.payment.client.dto.reponse.GetIamPortAccessTokenResDto;
import com.atticket.payment.client.dto.reponse.GetIamPortReceiptResDto;
import com.atticket.payment.client.dto.request.GetIamPortAccessTokenReqDto;
import com.atticket.payment.domain.Payment;
import com.atticket.payment.dto.request.ConfirmReceiptReqDto;
import com.atticket.payment.dto.response.ConfirmReceiptResDto;
import com.atticket.payment.type.LinkedPlatformType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final IamPortFeignClient iamPortFeignClient;

	public ConfirmReceiptResDto confrimReceipt(ConfirmReceiptReqDto confirmReceiptReqDto) {

		String paymentId = ""; //결제 id
		String orderId = ""; //주문 id
		String buyerTel = ""; //구매자 번호
		Long amount = null; //결제 금액
		LocalDateTime paidAt = null; //결제 시간

		if (confirmReceiptReqDto.getLinkedPlatform() == LinkedPlatformType.I_AM_PORT) {
			//결제 모듈에 조회 요청

			//1.accessToken 발급
			String accessToken = getIamPortAccessToken(confirmReceiptReqDto.getImpKey(),
				confirmReceiptReqDto.getImpSecret());
			log.debug("accessToken" + accessToken);

			//2.결제 내역 조회
			GetIamPortReceiptResDto.ResponseDto receiptResponse = getIamPortReceipt(accessToken,
				confirmReceiptReqDto.getPaymentId()).getResponse();
			log.debug("결제 내역 조회" + receiptResponse.toString());

			//결제 상태가 paid가 아니면 exception
			if (!receiptResponse.getStatus().equals("paid")) {
				throw new BaseException(INVALID_RECEIPT);
			}

			paymentId = receiptResponse.getImp_uid();
			orderId = receiptResponse.getMerchant_uid();
			buyerTel = receiptResponse.getBuyer_tel();
			amount = receiptResponse.getAmount();
			paidAt = Instant.ofEpochSecond(receiptResponse.getPaid_at())
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		}

		//결제 정보와 결제 모듈 측의 영수증 정보 비교
		//결제 Id 비교
		if (!confirmReceiptReqDto.getPaymentId().equals(paymentId)) {
			throw new BaseException(INVALID_RECEIPT);
		}
		//주문 Id 비교
		if (!confirmReceiptReqDto.getOrderId().equals(orderId)) {
			throw new BaseException(INVALID_RECEIPT);
		}
		//주문자 전화번호 비교
		if (!confirmReceiptReqDto.getBuyerTel().equals(buyerTel)) {
			throw new BaseException(INVALID_RECEIPT);
		}
		//합계금액 비교
		if (confirmReceiptReqDto.getAmount() != amount) {
			throw new BaseException(INVALID_RECEIPT);
		}

		//같은 결제 ID의 정보가 저장되어 있으면 exception
		if (!Objects.isNull(paymentRepository.findByPaymentId(confirmReceiptReqDto.getPaymentId()))) {
			throw new BaseException(INVALID_RECEIPT);
		} else {
			Payment payment = Payment.builder()
				.paymentId(confirmReceiptReqDto.getPaymentId())
				.orderId(confirmReceiptReqDto.getOrderId())
				.sellerId(confirmReceiptReqDto.getSellerId())
				.buyerId(confirmReceiptReqDto.getBuyerId())
				.buyerName(confirmReceiptReqDto.getBuyerName())
				.buyerTel(confirmReceiptReqDto.getBuyerTel())
				.linkedPlatform(confirmReceiptReqDto.getLinkedPlatform())
				.pgProvider(confirmReceiptReqDto.getPgProvider())
				.amount(confirmReceiptReqDto.getAmount())
				.paidAt(paidAt)
				.build();

			paymentRepository.save(payment);

			return new ConfirmReceiptResDto(paymentId, orderId);
		}
	}

	/**
	 * 아임포트 액세스토큰 조회
	 * @param restApiKey
	 * @param secretKey
	 * @return
	 */
	private String getIamPortAccessToken(String restApiKey, String secretKey) {
		GetIamPortAccessTokenResDto getIamPortAccessTokenResDto =
			iamPortFeignClient.getAccessToken(
				GetIamPortAccessTokenReqDto.builder()
					.imp_key(restApiKey)
					.imp_secret(secretKey)
					.build()
			);

		//조회 정보 존재하지 않을시
		if (Objects.isNull(getIamPortAccessTokenResDto.getResponse()) && getIamPortAccessTokenResDto.getCode()
			.equals("-1")) {
			throw new BaseException(INVALID_TOKEN);
		} else {
			return getIamPortAccessTokenResDto.getResponse().getAccess_token();
		}
	}

	/**
	 * 아임포트  결제 정보 조회
	 * @param accessToken
	 * @param paymentId
	 * @return
	 */
	private GetIamPortReceiptResDto getIamPortReceipt(String accessToken, String paymentId) {

		GetIamPortReceiptResDto getIamPortReceiptResDto =
			iamPortFeignClient.getReceipt(accessToken, paymentId);

		//조회 정보 존재하지 않을시
		if (Objects.isNull(getIamPortReceiptResDto.getResponse()) && getIamPortReceiptResDto.getCode().equals("-1")) {
			throw new BaseException(INVALID_RECEIPT);
		} else {
			return getIamPortReceiptResDto;
		}
	}

}
