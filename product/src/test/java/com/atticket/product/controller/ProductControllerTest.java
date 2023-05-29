package com.atticket.product.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Place;
import com.atticket.product.domain.Product;
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

		Product givenProduct = Product.builder()
			.name("테스트")
			.place(Place.builder().build())
			.build();
		List<Grade> givenGrades = Arrays.asList(Grade.builder().build());
		List<LocalDate> givenLocalDates = Arrays.asList(LocalDate.of(2023, 3, 1));

		//When
		when(productService.getProductById(productId)).thenReturn(givenProduct);
		when(gradeService.getGradesByProductId(productId)).thenReturn(givenGrades);
		when(showService.getShowDatesByProductId(productId)).thenReturn(givenLocalDates);

		BaseResponse<GetProductResDto> result = productController.getProduct(productId);

		//Then
		Assertions.assertEquals(result.getData().getProduct().getName(), givenProduct.getName());

	}

}
