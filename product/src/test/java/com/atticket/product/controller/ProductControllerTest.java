package com.atticket.product.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.dto.response.GetProductResDto;
import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ShowService;

public class ProductControllerTest {

	private ProductService productService = mock(ProductService.class);
	private ShowService showService = mock(ShowService.class);
	private GradeService gradeService = mock(GradeService.class);

	private ProductController productController;

	@BeforeEach
	public void setUpTest() {
		productController = new ProductController(productService, showService, gradeService);
	}

	@Test
	@DisplayName("상품 상세 조회 테스트")
	void getProductTest() {

		//Given
		Long productId = 1L;

		Product givenProduct = Product.builder().build();

		// BaseResponse<GetProductResDto> givenProduct = ok(GetProductResDto.construct(
		// 	Product.builder().build(), Arrays.asList(), Arrays.asList())
		// );

		//When
		when(productService.getProductById(productId)).thenReturn(givenProduct);
		BaseResponse<GetProductResDto> result = productController.getProduct(productId);

		//Then
		Assertions.assertEquals(result, givenProduct);
		//verify(productService).getProductById(1L);

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

		// mockMvc.perform(get("/products/" + productId + "/shows?date=" + date))
		// 	.andExpect(status().isOk())
		// 	.andExpect(jsonPath("$.data", notNullValue()))
		// 	.andExpect(jsonPath("$..shows", notNullValue()))
		// 	.andDo(print());

		//verify(showService).getShowDateByProductId(1L, LocalDate.of(2023, 3, 1));
	}

	@Test
	@DisplayName("없는 상품 상세 조회 테스트")
	void getProductNothingTest() throws Exception {

		//given
		given(productService.getProductById(11L)).willReturn(null);

		String productId = "11";
		//
		// mockMvc.perform(get("/products/" + productId))
		// 	.andExpect(status().isOk())
		// 	.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."))
		// 	.andDo(print());

		verify(productService).getProductById(11L);

	}

	@Test
	@DisplayName("상품 조회 validation 테스트")
	void getProductValidationTest() throws Exception {

		//given
		given(productService.getProductById(11L)).willReturn(null);

		String wrongId = "product";

		// mockMvc.perform(get("/products/" + wrongId))
		// 	.andExpect(status().isOk())
		//	.andExpect(jsonPath("$.code").value("500"))
		//		.andExpect(jsonPath("$.message").value("예상치 못한 에러가 발생했습니다."))
		//.andDo(print());

	}

}
