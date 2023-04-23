package com.atticket.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.atticket.product.domain.Product;
import com.atticket.product.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepository productRepo;

	@Test
	@DisplayName("상품상세조회")
	void getProductTest() throws Exception {

		//Given
		Long productId = 1L;

		//when
		Product result = productRepo.findById(productId).get();

		//then
		assertThat(result.getName()).isEqualTo("상품1");

	}

}
