package com.atticket.product.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.atticket.product.service.ProductService;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ProductService productService;

	@Test
	@DisplayName("mockMvc를 통한 상품 상세 조회 테스트")
	void getProductTest() throws Exception {

		//given
		String productId = "1";
		//
		mockMvc.perform(
				get("/products/" + productId)
			)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data", notNullValue()))
			.andDo(MockMvcResultHandlers.print());

	}

}
