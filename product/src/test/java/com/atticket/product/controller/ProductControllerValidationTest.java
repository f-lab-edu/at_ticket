package com.atticket.product.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ShowService;

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

//	@Test
//	@DisplayName("없는 상품 상세 조회 테스트")
//	void getProductNothingTest() throws Exception {
//
//		//given
//		given(productService.getProductById(11L)).willReturn(null);
//
//		String productId = "11";
//
//		mockMvc.perform(get("/products/" + productId))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."))
//			.andDo(print());
//
//	}
//
//	@Test
//	@DisplayName("상품 조회 validation 테스트")
//	void getProductValidationTest() throws Exception {
//
//		//given
//		given(productService.getProductById(11L)).willReturn(null);
//
//		String wrongId = "product";
//
//		mockMvc.perform(get("/products/" + wrongId))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.code").value("400"))
//			.andExpect(jsonPath("$.message").value("validation 에러입니다."))
//			.andDo(print());
//
//	}

}
