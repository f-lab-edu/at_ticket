package com.atticket.product.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ShowService;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

@WebMvcTest(ProductController.class)
public class ProductControllerValidationTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	ProductService productService;

	@MockBean
	ShowService showService;
	@MockBean
	GradeService gradeService;

	@Test
	@DisplayName("상품 상세 조회 테스트")
	void getProductTest() throws Exception {

		//given
		given(productService.getProductById(1L)).willReturn(
			Product.builder()
				.id(1L)
				.category(Category.MUSICAL)
				.subCategory(SubCategory.ORIGINAL)
				.name("상품1")
				.explain("설명")
				.runningTime(LocalTime.of(2, 0))
				.startDate(LocalDate.of(2023, 4, 21))
				.endDate(LocalDate.of(2024, 4, 21))
				.ageLimit(AgeLimit.FIFTEEN)
				.image("http://이미지.jpg")
				.region(Region.SEOUL)
				.build()

		);

		String productId = "1";

		mockMvc.perform(get("/products/" + productId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", notNullValue()))
			.andExpect(jsonPath("$.data.product.name").value(equalTo("상품1")))
			.andDo(print());

		verify(productService).getProductById(1L);

	}

	@Test
	@DisplayName("특정 날짜의 공연 리스트 조회")
	void getShowsTest() throws Exception {

		//given
		String productId = "1";
		String date = "20230301";

		given(showService.getShowDateByProductId(1L, LocalDate.of(2023, 3, 1))).willReturn(
			Arrays.asList(
				Show.builder()
					.id(1L)
					.time(LocalTime.of(10, 0, 0))
					.session(1)
					.date(LocalDate.of(2023, 3, 1))
					.productId(1L)
					.build(),
				Show.builder()
					.id(2L)
					.time(LocalTime.of(12, 0, 0))
					.session(2)
					.date(LocalDate.of(2023, 3, 1))
					.productId(1L)
					.build()
			)
		);

		mockMvc.perform(get("/products/" + productId + "/shows?date=" + date))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", notNullValue()))
			.andExpect(jsonPath("$..shows", notNullValue()))
			.andDo(print());

		//verify(showService).getShowDateByProductId(1L, LocalDate.of(2023, 3, 1));
	}

	@Test
	@DisplayName("없는 상품 상세 조회 테스트")
	void getProductNothingTest() throws Exception {

		//given
		given(productService.getProductById(11L)).willReturn(null);

		String productId = "11";

		mockMvc.perform(get("/products/" + productId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."))
			.andDo(print());

		verify(productService).getProductById(11L);

	}

	@Test
	@DisplayName("상품 조회 validation 테스트")
	void getProductValidationTest() throws Exception {

		//given
		given(productService.getProductById(11L)).willReturn(null);

		String wrongId = "product";

		mockMvc.perform(get("/products/" + wrongId))
			.andExpect(status().isOk())
			//	.andExpect(jsonPath("$.code").value("500"))
			//		.andExpect(jsonPath("$.message").value("예상치 못한 에러가 발생했습니다."))
			.andDo(print());

	}

}
