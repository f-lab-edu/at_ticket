package com.atticket.product.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Hall;
import com.atticket.product.domain.Seat;
import com.atticket.product.dto.request.GetSeatsInfoReqDto;
import com.atticket.product.dto.request.RegisterShowReqDto;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.dto.service.GetShowSeatsSvcDto;
import com.atticket.product.dto.service.RegisterShowServiceDto;
import com.atticket.product.repository.HallRepository;
import com.atticket.product.repository.ShowRepository;
import com.atticket.product.repository.ShowSeatRepository;
import com.atticket.product.repository.WishProductRepository;
import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ReservedSeatService;
import com.atticket.product.service.SeatService;
import com.atticket.product.service.ShowSeatService;
import com.atticket.product.service.ShowService;
import com.atticket.product.service.WishProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@WebMvcTest(ShowControllerTest.class)
class ShowControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ShowSeatService showSeatService;

	@MockBean
	private ShowService showService;

	@MockBean
	private ProductService productService;

	@MockBean
	private GradeService gradeService;

	@MockBean
	private SeatService seatService;

	@MockBean
	private ReservedSeatService reservedSeatService;

	@MockBean
	private WishProductService wishProductService;

	@MockBean
	private ShowRepository showRepository;

	@MockBean
	private ShowSeatRepository showSeatRepository;

	@MockBean
	private HallRepository hallRepository;

	@MockBean
	private WishProductRepository wishProductRepository;

	@MockBean
	private KafkaTemplate kafkaTemplate;

	@Test
	@DisplayName("공연의 전체 좌석 조회")
	void getRemainSeats() throws Exception {

		Long showId = 1L;

		GetShowSeatsSvcDto getRemainSeatsSvcDto = new GetShowSeatsSvcDto(
			Arrays.asList(Seat.builder()
				.id(1L)
				.space("1층")
				.locX("1")
				.locY("1")
				.seatRow("A")
				.seatRow("1L")
				.hall(Hall.builder()
					.build())
				.build())
			, Grade.builder().id(1L).type("A").price(1000).build()
		);

		when(showSeatService.getShowSeats(showId)).thenReturn(
			Arrays.asList(
				getRemainSeatsSvcDto
			)
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/shows/{showId}/seats", showId)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getRemainSeats",
				//Json 포매팅
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("showId").description("공연 ID")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
					fieldWithPath("data.seats[].id")
						.type(JsonFieldType.NUMBER)
						.description("좌석 이름"),
					fieldWithPath("data.seats[].space")
						.type(JsonFieldType.STRING)
						.description("영역 이름"),
					fieldWithPath("data.seats[].locX")
						.type(JsonFieldType.STRING)
						.description("x 좌표"),
					fieldWithPath("data.seats[].locY")
						.type(JsonFieldType.STRING)
						.description("y 좌표"),
					fieldWithPath("data.seats[].row")
						.type(JsonFieldType.STRING)
						.description("좌석 행"),
					fieldWithPath("data.seats[].rowNum")
						.type(JsonFieldType.NUMBER)
						.description("좌석 열"),
					fieldWithPath("data.seats[].grade")
						.type(JsonFieldType.STRING)
						.description("좌석 등급"),
					fieldWithPath("data.seats[].price")
						.type(JsonFieldType.NUMBER)
						.description("좌석 가격")
				)
			));
	}

	@Test
	@DisplayName("공연의 남은 좌석수 조회 테스트")
	void getRemainSeatsCnt() throws Exception {

		Long showId = 1L;

		//given
		given(showSeatService.getRemainSeatCntByShowId(showId)).willReturn(
			Arrays.asList(
				GetRemainSeatCntSvcDto.builder()
					.showId(1L)
					.gradeId(1L)
					.gradeNm("A")
					.seatCnt(5)
					.build(),
				GetRemainSeatCntSvcDto.builder()
					.showId(1L)
					.gradeId(2L)
					.gradeNm("B")
					.seatCnt(3)
					.build()
			)
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/shows/{showId}/seats/count", showId)
		);

		//then
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.remainSeats", notNullValue()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getRemainSeatsCnt",
				//Json 포매팅
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("showId").description("공연 ID")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
					fieldWithPath("data.remainSeats[].showId")
						.type(JsonFieldType.NUMBER)
						.description("공연 ID"),
					fieldWithPath("data.remainSeats[].gradeId")
						.type(JsonFieldType.NUMBER)
						.description("등급 ID"),
					fieldWithPath("data.remainSeats[].gradeNm")
						.type(JsonFieldType.STRING)
						.description("공연 이름"),
					fieldWithPath("data.remainSeats[].seatCnt")
						.type(JsonFieldType.NUMBER)
						.description("남은 좌석수")
				)
			));

		verify(showSeatService).getRemainSeatCntByShowId(1L);

	}

	@Test
	@DisplayName("공연 등록")
	void registerShow() throws Exception {

		Long productId = 1L;

		RegisterShowServiceDto registerShowServiceDto = RegisterShowServiceDto.builder()
			.showInfos(
				Arrays.asList(
					RegisterShowServiceDto.ShowInfo.builder()
						.session(1)
						.date(LocalDate.of(2023, 5, 5))
						.time(LocalTime.of(10, 0, 0))
						.hallId(1L)
						.seatInfos(
							Arrays.asList(
								RegisterShowServiceDto.SeatInfo.builder()
									.gradeId(1L)
									.seatIds(
										Arrays.asList(1L, 2L, 3L)
									)
									.build()
							)
						)
						.build()
				)
			).build();

		given(showSeatService.registerShow(productId, registerShowServiceDto)).willReturn(
			Arrays.asList(1L, 2L, 3L)
		);

		RegisterShowReqDto reqDto =
			RegisterShowReqDto.builder()
				.shows(
					Arrays.asList(
						RegisterShowReqDto.ShowInfo.builder()
							.session(1)
							.date(LocalDate.of(2023, 5, 5))
							.time(LocalTime.of(10, 0, 0))
							.hallId(1L)
							.seats(
								Arrays.asList(
									RegisterShowReqDto.SeatInfo.builder()
										.grade(1L)
										.ids(Arrays.asList(1L, 2L, 3L))
										.build()
								)
							)
							.build()
					)

				)
				.build();

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/shows/product/{productId}", productId)
				.content(objectMapper.writeValueAsString(reqDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("registerShow",
				//Json 포매팅
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 ID")
				),
				requestFields(
					fieldWithPath("shows[].date")
						.type(JsonFieldType.STRING)
						.description("공연 날짜"),
					fieldWithPath("shows[].time")
						.type(JsonFieldType.STRING)
						.description("공연 시간"),
					fieldWithPath("shows[].session")
						.type(JsonFieldType.NUMBER)
						.description("공연 순번"),
					fieldWithPath("shows[].hallId")
						.type(JsonFieldType.NUMBER)
						.description("공연장 ID"),
					fieldWithPath("shows[].seats[].grade")
						.type(JsonFieldType.NUMBER)
						.description("공연 등급 ID"),
					fieldWithPath("shows[].seats[].ids")
						.type(JsonFieldType.ARRAY)
						.description("좌석 ID")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
					fieldWithPath("data.showIds[]")
						.type(JsonFieldType.ARRAY)
						.description("등록된 공연 ID")
				)
			));

	}

	@Test
	@DisplayName("공연 좌석 가격 조회")
	void getSeatsPrice() throws Exception {

		Long showId = 1L;
		List<Long> seatIds = List.of(1L, 2L, 3L);
		int returnPrice = 1000;

		GetSeatsInfoReqDto reqDto = new GetSeatsInfoReqDto(seatIds);

		given(showSeatService.getSeatsPrice(showId, seatIds)).willReturn(
			returnPrice
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/shows/{showId}/seats/price", showId)
				.content(objectMapper.writeValueAsString(reqDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getSeatsPrice",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					pathParameters(
						parameterWithName("showId").description("공연 ID")
					),
					requestFields(
						fieldWithPath("seatIds")
							.type(JsonFieldType.ARRAY)
							.description("좌석 ID")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data")
							.type(JsonFieldType.NUMBER)
							.description("가격")
					)
				)
			);

	}

}
