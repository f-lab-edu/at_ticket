package com.atticket.product.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Place;
import com.atticket.product.domain.Product;
import com.atticket.product.repository.HallRepository;
import com.atticket.product.repository.SeatRepository;
import com.atticket.product.repository.ShowRepository;
import com.atticket.product.repository.ShowSeatRepository;
import com.atticket.product.repository.WishProductRepository;
import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ReservedSeatService;
import com.atticket.product.service.ShowService;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

@AutoConfigureRestDocs
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@MockBean
	private ShowService showService;
	@MockBean
	private GradeService gradeService;

	@MockBean
	private ReservedSeatService reservedSeatService;

	@MockBean
	private ShowRepository showRepository;

	@MockBean
	private SeatRepository seatRepository;

	@MockBean
	private ShowSeatRepository showSeatRepository;

	@MockBean
	private WishProductRepository wishProductRepository;

	@MockBean
	private HallRepository hallRepository;

	@MockBean
	private ProductController productController;

	@MockBean
	private KafkaTemplate kafkaTemplate;

	@Test
	@DisplayName("상품 상세 조회 테스트")
	void getProductTest() throws Exception {

		// Create a ProductDto for testing

		Long productId = 1L;

		// Create a LocalDate list for testing
		List<LocalDate> showDatesList = showDatesList = Arrays.asList(

		);

		// Mock the service calls
		when(productService.getProductById(productId)).thenReturn(
			Product.builder()
				.id(1L)
				.name("캣츠")
				.explains("공연설명")
				.category(Category.MUSICAL)
				.subCategory(SubCategory.ORIGINAL)
				.runningTime(LocalTime.of(02, 00, 00))
				.interMission(LocalTime.of(20, 00, 00))
				.startDate(LocalDate.of(2023, 3, 1))
				.endDate(LocalDate.of(2024, 06, 30))
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
		//
		// GetProductResDto getProductResDto = new GetProductResDto(
		//
		// 	GetProductResDto.ProductDto.builder()
		// 		.name("캣츠")
		// 		.explain("공연설명")
		// 		.category(Category.MUSICAL)
		// 		.subCategory(SubCategory.ORIGINAL)
		// 		.runningTime(LocalTime.of(02, 00, 00))
		// 		.startDate(LocalDate.of(2023, 3, 1))
		// 		.endDate(LocalDate.of(2024, 06, 30))
		// 		.image("http://image.img")
		// 		.ageLimit(AgeLimit.TWELVE)
		// 		.build(),
		// 	Arrays.asList(
		// 		GetProductResDto.GradeDto.builder()
		// 			.type("A")
		// 			.price(1000)
		// 			.build()
		// 	),
		// 	Arrays.asList(
		// 		LocalDate.of(2023, 3, 1),
		// 		LocalDate.of(2023, 3, 2)
		// 	)
		// );
		//
		// given(GetProductResDto.construct(
		// 	productService.getProductById(productId)
		// 	, gradeService.getGradesByProductId(productId)
		// 	, showService.getShowDatesByProductId(productId)))
		// 	.willReturn(getProductResDto);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/products/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());

	}
}
