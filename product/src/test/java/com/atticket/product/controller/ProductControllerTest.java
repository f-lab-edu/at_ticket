package com.atticket.product.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Place;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
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
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SortOption;
import com.atticket.product.type.SubCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@WebMvcTest(ProductControllerTest.class)
class ProductControllerTest {

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
	@DisplayName("상품 조회")
	void getProducts() throws Exception {

		given(productService.getProducts(1, 10, null,
			null, null, null, null,
			null, SortOption.RECENT)).willReturn(
			Arrays.asList(
				Product.builder()
					.id(1L)
					.name("캣츠")
					.explains("공연설명")
					.category(Category.MUSICAL)
					.subCategory(SubCategory.ORIGINAL)
					.runningTime(LocalTime.of(2, 0, 0))
					.interMission(LocalTime.of(20, 0, 0))
					.startDate(LocalDate.of(2023, 5, 31))
					.endDate(LocalDate.of(2024, 6, 30))
					.image("http://image.img")
					.ageLimit(AgeLimit.TWELVE)
					.place(
						Place.builder()
							.id(1L)
							.name("서울 공연장1")
							.address("서울 어쩌구구 저쩌구동")
							.phoneNumber("010 123 1234")
							.region(Region.SEOUL)
							.build()
					).build()
			)
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/products"));

		//then
		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getProducts",
				//Json 포매팅
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParameters(
					parameterWithName("page")
						.description("페이지 넘버")
						.optional(),
					parameterWithName("perPage")
						.description("페이당 표시 수")
						.optional(),
					parameterWithName("keyword")
						.optional()
						.description("키워드"),
					parameterWithName("category")
						.optional()
						.description("카테고리"),
					parameterWithName("subCategory")
						.optional()
						.description("서브카테고리"),
					parameterWithName("region")
						.optional()
						.description("지역"),
					parameterWithName("startDate")
						.optional()
						.description("공연 시작 날짜"),
					parameterWithName("endDate")
						.optional()
						.description("공연 종료 날짜"),
					parameterWithName("sortOption")
						.optional()
						.description("정렬 옵션")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
					fieldWithPath("data.products[].id")
						.type(JsonFieldType.NUMBER)
						.description("공연 ID"),
					fieldWithPath("data.products[].name")
						.type(JsonFieldType.STRING)
						.description("공연 이름"),
					fieldWithPath("data.products[].category")
						.type(JsonFieldType.STRING)
						.description("공연 카테고리"),
					fieldWithPath("data.products[].subCategory")
						.type(JsonFieldType.STRING)
						.description("공연 서브 카테고리"),
					fieldWithPath("data.products[].image")
						.type(JsonFieldType.STRING)
						.description("공연 이미지"),
					fieldWithPath("data.products[].place.id")
						.type(JsonFieldType.NUMBER)
						.description("공연 장소 ID"),
					fieldWithPath("data.products[].place.name")
						.type(JsonFieldType.STRING)
						.description("공연장소 이름"),
					fieldWithPath("data.products[].place.region")
						.type(JsonFieldType.STRING)
						.description("공연 장소 지역"),
					fieldWithPath("data.products[].startDate")
						.type(JsonFieldType.STRING)
						.description("공연 시작일"),
					fieldWithPath("data.products[].endDate")
						.type(JsonFieldType.STRING)
						.description("공연 종료일"),
					fieldWithPath("data.products[].runningTime")
						.type(JsonFieldType.STRING)
						.description("공연 상영시간"),
					fieldWithPath("data.products[].interMission")
						.type(JsonFieldType.STRING)
						.description("공연 휴식시간"),
					fieldWithPath("data.products[].ageLimit")
						.type(JsonFieldType.STRING)
						.description("공연 관람 제한 나이")
				)
			));
	}

	@Test
	@DisplayName("상품 상세 조회 테스트")
	void getProduct() throws Exception {

		Long productId = 1L;

		// Mock the service calls
		when(productService.getProductById(productId)).thenReturn(
			Product.builder()
				.id(1L)
				.name("캣츠")
				.explains("공연설명")
				.category(Category.MUSICAL)
				.subCategory(SubCategory.ORIGINAL)
				.runningTime(LocalTime.of(2, 0, 0))
				.interMission(LocalTime.of(20, 0, 0))
				.startDate(LocalDate.of(2023, 3, 1))
				.endDate(LocalDate.of(2024, 6, 30))
				.image("http://image.img")
				.ageLimit(AgeLimit.TWELVE)
				.place(
					Place.builder()
						.id(1L)
						.name("서울 공연장1")
						.address("서울 어쩌구구 저쩌구동")
						.phoneNumber("010 123 1234")
						.region(Region.SEOUL)
						.build()
				).build()
		);
		when(gradeService.getGradesByProductId(productId)).thenReturn(
			Arrays.asList(Grade.builder().id(1L).type("A").price(1000).build())
		);
		when(showService.getShowDatesByProductId(anyLong())).thenReturn(
			Arrays.asList(
				LocalDate.of(2023, 3, 1),
				LocalDate.of(2023, 3, 2)
			)
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/products/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		verify(productService).getProductById(productId);

		//then
		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getProduct",
				//Json 포매팅
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 ID")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
					fieldWithPath("data.product.name")
						.type(JsonFieldType.STRING)
						.description("공연 이름"),
					fieldWithPath("data.product.explain")
						.type(JsonFieldType.STRING)
						.description("등급 설명"),
					fieldWithPath("data.product.category")
						.type(JsonFieldType.STRING)
						.description("공연 카테고리"),
					fieldWithPath("data.product.subCategory")
						.type(JsonFieldType.STRING)
						.description("공연 서브 카테고리"),
					fieldWithPath("data.product.runningTime")
						.type(JsonFieldType.STRING)
						.description("공연 상영시간"),
					fieldWithPath("data.product.startDate")
						.type(JsonFieldType.STRING)
						.description("공연 시작일"),
					fieldWithPath("data.product.endDate")
						.type(JsonFieldType.STRING)
						.description("공연 종료일"),
					fieldWithPath("data.product.ageLimit")
						.type(JsonFieldType.STRING)
						.description("공연 관람 제한 나이"),
					fieldWithPath("data.product.image")
						.type(JsonFieldType.STRING)
						.description("공연 이미지"),
					fieldWithPath("data.product.region")
						.type(JsonFieldType.STRING)
						.description("공연 상연 지역"),
					fieldWithPath("data.showDates[]")
						.type(JsonFieldType.ARRAY)
						.description("공연 지역"),
					fieldWithPath("data.seatGrades[].type")
						.type(JsonFieldType.STRING)
						.description("좌석 등급"),
					fieldWithPath("data.seatGrades[].price")
						.type(JsonFieldType.NUMBER)
						.description("좌석 가격")
				)
			));

	}

	@Test
	@DisplayName("일자별 공연 조회")
	void getShows() throws Exception {

		Long productId = 1L;
		String dateStr = "2023-05-31";

		LocalDate dateParse = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		given(showService.getShowDateByProductId(productId, dateParse)).willReturn(
			Arrays.asList(
				Show.builder()
					.id(1L)
					.product(Product.builder()
						.id(1L)
						.build())
					.session(1)
					.time(LocalTime.of(10, 0, 0))
					.date(LocalDate.of(2023, 5, 31))
					.build()
			)
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/products/{productId}/shows?date={dateStr}", productId, dateStr)
		);

		//then
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.shows[0].id").value(1))
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("getShows",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("공연 ID")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
					fieldWithPath("data.shows[].id")
						.type(JsonFieldType.NUMBER)
						.description("공연 ID"),
					fieldWithPath("data.shows[].session")
						.type(JsonFieldType.NUMBER)
						.description("공연 순번"),
					fieldWithPath("data.shows[].time")
						.type(JsonFieldType.STRING)
						.description("공연 시각")
				)
			));

		verify(showService).getShowDateByProductId(productId, dateParse);
	}

	@Test
	@DisplayName("상품 삭제")
	void deleteProduct() throws Exception {

		Long productId = 1L;

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/products/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("deleteProduct",
				//Json 포매팅
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 ID")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지")
				)
			));
	}
}
