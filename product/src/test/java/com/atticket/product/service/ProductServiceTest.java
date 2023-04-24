package com.atticket.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.atticket.common.response.BaseException;
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
		Product result = productService.getProductById(productId);

		//then
		assertThat(result.getName()).isEqualTo("상품1");

	}

	@Test
	@DisplayName("상품 삭제 ")
	void deleteProductTest() throws Exception {

		//Given
		Long productId = 1L;

		//when
		//상품 조회 확인
		Product result = productService.getProductById(productId);
		System.out.println("result.getId() : " + result.getId());

		//삭제
		productService.deleteProduct(productId);

		//같은 id로 삭제 시 에러 검증
		Assertions.assertThrows(BaseException.class, () -> productService.deleteProduct(productId));

	}

}
