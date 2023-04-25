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

@AutoConfigureMockMvc
@SpringBootTest
class ShowControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("공연의 남은 좌석수 조회 테스트")
	void getShowsTest() throws Exception {

		//given
		String showId = "1";

		//
		mockMvc.perform(
				get("/shows/" + showId + "/seats/count")
			)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data", notNullValue()))
			.andDo(MockMvcResultHandlers.print());

	}
}
