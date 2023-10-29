package com.atticket.payment.controller;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.atticket.payment.Repository.PaymentRepository;
import com.atticket.payment.client.client.IamPortFeignClient;
import com.atticket.payment.client.dto.reponse.PostIamPortPaymentCancelResDto;
import com.atticket.payment.client.dto.request.PostIamPortPaymentCancelReqDto;
import com.atticket.payment.dto.request.ConfirmReceiptReqDto;
import com.atticket.payment.dto.request.PostCancelPaymentReqDto;
import com.atticket.payment.dto.response.ConfirmReceiptResDto;
import com.atticket.payment.dto.response.PostCancelPaymentResDto;
import com.atticket.payment.service.PaymentService;
import com.atticket.payment.type.LinkedPlatformType;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PaymentService paymentService;

	@MockBean
	private PaymentRepository paymentRepository;

	@MockBean
	private IamPortFeignClient iamPortFeignClient;

	@Test
	@DisplayName("결제 확인")
	void confirmReceipt() throws Exception {

		String paymentId = "imp_221562613272";
		Long reservationId = 1000L;
		Long amount = 3000L;
		String orderId = "IMP355";

		ConfirmReceiptReqDto confirmReceiptReqDto = new ConfirmReceiptReqDto(paymentId, reservationId, amount);

		ConfirmReceiptResDto confirmReceiptResDto = new ConfirmReceiptResDto(paymentId, orderId);

		when(paymentService.confirmReceipt(paymentId, reservationId, amount)).thenReturn(
			confirmReceiptResDto
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/payments/")
				.content(objectMapper.writeValueAsString(confirmReceiptReqDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("confirmReceipt",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("paymentId").description("결제 ID"),
						fieldWithPath("reservationId").description("예약 ID"),
						fieldWithPath("amount").description("가격")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data.paymentId")
							.type(JsonFieldType.STRING)
							.description("결제 ID"),
						fieldWithPath("data.orderId")
							.type(JsonFieldType.STRING)
							.description("주문 ID")
					)
				)
			);
	}

	@Test
	@DisplayName("결제 취소")
	void postCancelPayment() throws Exception {

		String paymentId = "imp_221562613272";
		String orderId = "IMP355";
		Double amount = 3000.0;    //취소 금액
		Double taxFree = 100.0;        //취소요청금액 중 면세금액
		String reason = null;        //취소 사유
		String refundHolder = null;        //환불계좌 예금주
		String refundTel = null;        // 환불계좌 예금주 연락처
		String refundBank = null;        //환불계좌 은행
		String refundAccount = "null";        //환불계좌 계좌번호
		LinkedPlatformType linkedPlatform = LinkedPlatformType.I_AM_PORT;        //결제 연동 타입

		String impKey = null;        //(아임포트 판매자) API 키
		String impSecret = null;    //(아임포트 판매자) 비밀 키

		PostCancelPaymentReqDto postCancelPaymentReqDto = new PostCancelPaymentReqDto(
			paymentId, orderId, amount, taxFree, reason, refundHolder, refundTel, refundBank, refundAccount,
			linkedPlatform, impKey, impSecret
		);

		PostCancelPaymentResDto postCancelPaymentResDto = new PostCancelPaymentResDto(paymentId, orderId);

		when(paymentService.cancelPayment(postCancelPaymentReqDto)).thenReturn(
			postCancelPaymentResDto
		);

		when(iamPortFeignClient.postPaymentCancel(
			"accessTocken",
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
		)).thenReturn(
			new PostIamPortPaymentCancelResDto("0", "",
				PostIamPortPaymentCancelResDto.ResponseDto.builder()
					.imp_uid("test uid")
					.merchant_uid("merchant_uid")
					.build(),
				new PostIamPortPaymentCancelResDto.CancelHistory()
			)
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/payments/cancel")
				.content(objectMapper.writeValueAsString(postCancelPaymentReqDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		verify(paymentService).cancelPayment(refEq(postCancelPaymentReqDto));

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("postCancelPayment",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("paymentId").type(JsonFieldType.STRING).description("결제 ID"),
						fieldWithPath("orderId").type(JsonFieldType.STRING).description("주문 ID"),
						fieldWithPath("amount").type(JsonFieldType.NUMBER).description("금액").optional(),
						fieldWithPath("taxFree").type(JsonFieldType.NUMBER).description("면세 금액").optional(),
						fieldWithPath("reason").type(JsonFieldType.STRING).description("취소 사유").optional(),
						fieldWithPath("refundHolder").type(JsonFieldType.STRING).description("환불계좌 예금주").optional(),
						fieldWithPath("refundTel").type(JsonFieldType.STRING).description("환불계좌 예금주 연락처").optional(),
						fieldWithPath("refundBank").type(JsonFieldType.STRING).description("환불계좌 은행").optional(),
						fieldWithPath("refundAccount").type(JsonFieldType.STRING).description("환불계좌 계좌번호").optional(),
						fieldWithPath("linkedPlatform").type(JsonFieldType.STRING).description("결제 연동 타입").optional(),
						fieldWithPath("impKey").type(JsonFieldType.STRING).description("API 키").optional(),
						fieldWithPath("impSecret").type(JsonFieldType.STRING).description("비밀 키").optional()
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data.paymentId")
							.type(JsonFieldType.STRING)
							.optional()
							.description("결제 ID"),
						fieldWithPath("data.orderId")
							.type(JsonFieldType.STRING)
							.optional()
							.description("주문 ID")
					)
				)
			);
	}
}
