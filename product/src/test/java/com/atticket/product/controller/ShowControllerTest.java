package com.atticket.product.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
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

@AutoConfigureRestDocs
@WebMvcTest(ShowControllerTest.class)
class ShowControllerTest {

	@Autowired
	private MockMvc mockMvc;

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

	// @MockBean
	private WishProductService wishProductService;

	@MockBean
	private ShowRepository showRepository;

	@MockBean
	private ShowSeatRepository showSeatRepository;

	@MockBean
	private HallRepository hallRepository;

	@MockBean
	private KafkaTemplate kafkaTemplate;

	@MockBean
	private WishProductRepository wishProductRepository;

	@Test
	@DisplayName("공연의 남은 좌석수 조회 테스트")
	void getShowsTest() throws Exception {

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
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.remainSeats", notNullValue()))
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
}
