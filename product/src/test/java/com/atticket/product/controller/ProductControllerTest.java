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

	@Test
	@DisplayName("일자별 공연 조회 테스트")
	void getShowsTest() throws Exception {

		//given
		String productId = "1";

		//given
		String date = "20230401";

		//
		mockMvc.perform(
				get("/products/" + productId + "/shows?date=" + date)
			)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data", notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$..shows", notNullValue()))
			.andDo(MockMvcResultHandlers.print());

	}

}
