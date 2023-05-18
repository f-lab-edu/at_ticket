package com.atticket.product.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atticket.common.response.BaseException;
import com.atticket.product.domain.Product;
import com.atticket.product.repository.ProductRepository;

public class ProductServiceTest {

	private ProductRepository productRepository = mock(ProductRepository.class);
	private ProductService productService;

	@BeforeEach
	public void setUpTest() {
		productService = new ProductService(productRepository);
	}

	@Test
	@DisplayName("상품 조회 ")
	void getProductById() {

		//Given
		Long productId = 1L;
		Product givenProduct = Product.builder().build();

		//When
		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));
		Product result = productService.getProductById(productId);

		//Then
		Assertions.assertEquals(result, givenProduct);
		verify(productRepository).findById(1L);

	}

	@Test
	@DisplayName("상품 삭제")
	void deleteProduct() {

		//Given
		Long productId = 1L;
		Product givenProduct = Product.builder().build();

		//When
		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));
		productService.deleteProduct(productId);

		//Then
		verify(productRepository).deleteById(productId);
	}

	@Test
	@DisplayName("상품 삭제 에러")
	void deleteProductNotFindId() {

		//Given
		Long productId = 1L;

		//When
		when(productRepository.findById(productId)).thenReturn(null);

		//Then
		Assertions.assertThrows(BaseException.class, () -> productService.deleteProduct(productId));

	}

}
