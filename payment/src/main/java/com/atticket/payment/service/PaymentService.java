package com.atticket.payment.service;

import static com.atticket.common.response.BaseStatus.INVALID_RECEIPT;
import static com.atticket.common.response.BaseStatus.INVALID_TOKEN;
import static com.atticket.common.response.BaseStatus.UNEXPECTED_ERROR;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.atticket.common.response.BaseException;
import com.atticket.payment.Repository.PaymentRepository;
import com.atticket.payment.client.client.IamPortFeignClient;
import com.atticket.payment.client.dto.reponse.GetIamPortAccessTokenResDto;
import com.atticket.payment.client.dto.reponse.GetIamPortReceiptResDto;
import com.atticket.payment.client.dto.reponse.PostIamPortPaymentCancelResDto;
import com.atticket.payment.client.dto.request.GetIamPortAccessTokenReqDto;
import com.atticket.payment.client.dto.request.PostIamPortPaymentCancelReqDto;
import com.atticket.payment.domain.Payment;
import com.atticket.payment.dto.request.PostCancelPaymentReqDto;
import com.atticket.payment.dto.response.ConfirmReceiptResDto;
import com.atticket.payment.dto.response.PostCancelPaymentResDto;
import com.atticket.payment.type.LinkedPlatformType;
import com.atticket.payment.type.PgProviderType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final IamPortFeignClient iamPortFeignClient;

	//(아임포트 판매자) API 키
	@Value("${iamport.api.key}")
	private String impKey;

	//(아임포트 판매자) 비밀 키
	@Value("${iamport.api.secret}")
	private String impSecret;

	public PostCancelPaymentResDto cancelPayment(PostCancelPaymentReqDto postCancelPaymentReqDto) {

		String paymentId = ""; //결제 id
		String orderId = ""; //주문 id

		if (postCancelPaymentReqDto.getLinkedPlatform() == LinkedPlatformType.I_AM_PORT) {

			//1.accessToken 발급
			String accessToken = getIamPortAccessToken(postCancelPaymentReqDto.getImpKey(),
				postCancelPaymentReqDto.getImpSecret());
			log.debug("accessToken" + accessToken);

			//2.취소 요청
			PostIamPortPaymentCancelResDto postIamPortPaymentCancelResDto = iamPortFeignClient.postPaymentCancel(
				accessToken,
				PostIamPortPaymentCancelReqDto.builder()
					.imp_uid(postCancelPaymentReqDto.getPaymentId())
					.merchant_uid(postCancelPaymentReqDto.getOrderId())
					.amount(
						postCancelPaymentReqDto.getAmount())
					.tax_free(
						postCancelPaymentReqDto.getTaxFree())
					.reason(postCancelPaymentReqDto.getReason())
					.refund_holder(postCancelPaymentReqDto.getRefundHolder())
					.refund_account(postCancelPaymentReqDto.getRefundAccount())
					.refund_bank(postCancelPaymentReqDto.getRefundBank())
					.refund_tel(postCancelPaymentReqDto.getRefundTel())
					.build()
			);

			if (Objects.isNull(postIamPortPaymentCancelResDto.getResponse()) && postIamPortPaymentCancelResDto.getCode()
				.equals("-1")) {
				throw new BaseException(INVALID_TOKEN);
			}

			//0이면 정상 조회 아니면  message에 오류 메세지 포함
			if (!postIamPortPaymentCancelResDto.getCode().equals("0")) {
				log.debug("cancelPayment error_message" + postIamPortPaymentCancelResDto.getMessage());
				throw new BaseException(UNEXPECTED_ERROR);
			}

			paymentId = postIamPortPaymentCancelResDto.getResponse().getImp_uid();
			orderId = postIamPortPaymentCancelResDto.getResponse().getMerchant_uid();
		}

		return new PostCancelPaymentResDto(paymentId, orderId);

	}

	public ConfirmReceiptResDto confirmReceipt(String paymentId, Long reservationId, Long amount) {

		String userToken =
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
				.getHeader("Authorization");

		//1.accessToken 발급
		String accessToken = getIamPortAccessToken(this.impKey, this.impSecret);
		log.debug("accessToken" + accessToken);

		//2.결제 내역 조회
		GetIamPortReceiptResDto.ResponseDto receiptRes = getIamPortReceipt(accessToken,
			paymentId).getResponse();
		log.debug("결제 내역 조회" + receiptRes.toString());

		// 예약 번호가 맞지 않다면 exception
		if (!reservationId.equals(receiptRes.getCustom_data().getReservation_id())) {
			log.debug("예약 id 불일치");
			throw new BaseException(INVALID_RECEIPT);
		}

		// 금액이 맞지 않다면 exception
		if (!amount.equals(receiptRes.getAmount())) {
			log.debug("가격 불일치");
			throw new BaseException(INVALID_RECEIPT);
		}

		Payment payment = paymentRepository.findByPaymentId(receiptRes.getImp_uid());
		// 결제 정보가 DB 추가되어 있지 않으면 추가
		if (Objects.isNull(payment)) {
			LocalDateTime paidAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(receiptRes.getPaid_at()),
				TimeZone.getDefault().toZoneId());
			payment = Payment.builder()
				.paymentId(receiptRes.getImp_uid())
				.orderId(receiptRes.getMerchant_uid())
				.reservationId(receiptRes.getCustom_data().getReservation_id())
				.sellerId(receiptRes.getCustom_data().getSeller_id())
				.buyerId(receiptRes.getCustom_data().getBuyer_id())
				.buyerName(receiptRes.getBuyer_name())
				.buyerTel(receiptRes.getBuyer_tel())
				.linkedPlatform(LinkedPlatformType.I_AM_PORT)
				.pgProvider(PgProviderType.findByValue(receiptRes.getPg_provider()))
				.amount(receiptRes.getAmount())
				.paidAt(paidAt)
				.build();

			paymentRepository.save(payment);
		}

		return new ConfirmReceiptResDto(paymentId, receiptRes.getMerchant_uid());
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
