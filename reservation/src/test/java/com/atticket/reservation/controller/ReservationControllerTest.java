package com.atticket.reservation.controller;

import com.atticket.common.response.BaseResponse;
import com.atticket.reservation.domain.Reservation;
import com.atticket.reservation.dto.request.PreRegisterReservationReqDto;
import com.atticket.reservation.dto.request.RegisterReservationReqDto;
import com.atticket.reservation.dto.response.GetReservationResDto;
import com.atticket.reservation.dto.response.RegisterReservationResDto;
import com.atticket.reservation.type.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ReservationController reservationController;

	@Test
	@DisplayName("예약조회")
	void getReservation() throws Exception {
		String userId = "kimjunbo";
		Long id = 1L;

		GetReservationResDto res = GetReservationResDto.construct(new Reservation(id, userId, Arrays.asList(1L, 2L), Status.WAIT_PAY, LocalDateTime.now(), 1L));

		given(reservationController.getReservation(id)).willReturn(BaseResponse.ok(res));

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/reservations/{reservationId}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getReservation",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					pathParameters(
						parameterWithName("reservationId").description("예약 id")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data.id")
							.type(JsonFieldType.NUMBER)
							.description("예약 id").optional(),
						fieldWithPath("data.userId")
							.type(JsonFieldType.STRING)
							.description("사용자 id").optional(),
						fieldWithPath("data.seatIds")
							.type(JsonFieldType.ARRAY)
							.description("좌석 id 리스트").optional(),
						fieldWithPath("data.status")
							.type(JsonFieldType.STRING)
							.description("예약 상태").optional(),
						fieldWithPath("data.time")
							.type(JsonFieldType.STRING)
							.description("예약 시간").optional(),
						fieldWithPath("data.showId")
							.type(JsonFieldType.NUMBER)
							.description("공연 id").optional()
					)
				)
			);
	}


	@Test
	@DisplayName("선예약하기")
	void preRegisterReservation() throws Exception {
		Long showId = 1L;
		List<Long> seatIds = Arrays.asList(1L, 2L);
		Long reservationId = 1L;

		PreRegisterReservationReqDto req = new PreRegisterReservationReqDto();
		req.setShowId(showId);
		req.setSeatIds(seatIds);

		RegisterReservationResDto res = RegisterReservationResDto.construct(reservationId);

		given(reservationController.preRegisterReservation(any())).willReturn(BaseResponse.ok(res));

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/reservations/pre")
				.content(objectMapper.writeValueAsString(req))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("preRegisterReservation",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("showId").description("공연 ID"),
						fieldWithPath("seatIds").description("좌석 ID 리스트")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data.reservationId")
							.type(JsonFieldType.NUMBER)
							.description("예약 id").optional()
					)
				)
			);
	}

	@Test
	@DisplayName("예약하기")
	void registerReservation() throws Exception {
		String paymentId = "imp_078249949476";
		Long reservationId = 1L;


		RegisterReservationReqDto req = new RegisterReservationReqDto();
		req.setReservationId(reservationId);
		req.setPaymentId(paymentId);

		RegisterReservationResDto res = RegisterReservationResDto.construct(reservationId);

		given(reservationController.registerReservation(any())).willReturn(BaseResponse.ok(res));

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/reservations")
				.content(objectMapper.writeValueAsString(req))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("registerReservation",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("reservationId").description("예약 ID"),
						fieldWithPath("paymentId").description("결제 영수증 ID")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data.reservationId")
							.type(JsonFieldType.NUMBER)
							.description("예약 id").optional()
					)
				)
			);
	}

	@Test
	@DisplayName("예약된 좌석 id 리스트 조회")
	void getReservationSeatIds() throws Exception {
		Long showId = 1L;
		List<Long> seatIds = Arrays.asList(1L, 2L);

		given(reservationController.getReservationSeatIds(showId)).willReturn(BaseResponse.ok(seatIds));

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/reservations//show/{showId}/seats", showId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getReservationSeatIds",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					pathParameters(
						parameterWithName("showId").description("공연 id")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data")
							.type(JsonFieldType.ARRAY)
							.description("좌석 id 리스트").optional()
					)
				)
			);
	}

	@Test
	@DisplayName("선예약된 좌석 id 리스트 조회")
	void getPreReservationSeatIds() throws Exception {
		Long showId = 1L;
		List<Long> seatIds = Arrays.asList(1L, 2L);

		given(reservationController.getPreReservationSeatIds(showId)).willReturn(BaseResponse.ok(seatIds));

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/reservations//show/{showId}/seats/pre", showId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getPreReservationSeatIds",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					pathParameters(
						parameterWithName("showId").description("공연 id")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data")
							.type(JsonFieldType.ARRAY)
							.description("좌석 id 리스트").optional()
					)
				)
			);
	}


}
