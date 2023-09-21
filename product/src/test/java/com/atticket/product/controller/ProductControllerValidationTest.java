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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(ProductController.class)
public class ProductControllerValidationTest {

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
	@DisplayName("없는 상품 상세 조회 테스트")
	void getProductNothingTest() throws Exception {

		//given
		given(productService.getProductById(11L)).willReturn(null);

		Long productId = 1L;

		this.mockMvc.perform(get("/products/{productId}", productId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("조회된 데이터가 없습니다."))
			.andDo(print());

	}

	@Test
	@DisplayName("상품 조회 validation 테스트")
	void getProductValidationTest() throws Exception {

		//given
		given(productService.getProductById(11L)).willReturn(null);

		String wrongId = "product";

		this.mockMvc.perform(get("/products/" + wrongId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("validation 에러입니다."))
			.andDo(print());

	}

}
