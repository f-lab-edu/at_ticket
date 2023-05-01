package com.atticket.product.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atticket.product.domain.Product;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

public class ProductServiceTest {

	private ProductRepository productRepository = mock(ProductRepository.class);
	private ProductService productService;

	@BeforeEach
	public void setUpTest() {
		productService = new ProductService(productRepository);
	}

	@Test
	@DisplayName("상품 조회 ")
	void getProductById() throws Exception {

		//Given
		Long productId = 1L;

		Product givenProduct = Product.builder()
			.id(productId)
			.category(Category.MUSICAL)
			.subCategory(SubCategory.ORIGINAL)
			.name("상품1")
			.explain("설명")
			.runningTime(LocalTime.of(2, 0))
			.startDate(LocalDate.of(2023, 4, 21))
			.endDate(LocalDate.of(2024, 4, 21))
			.ageLimit(AgeLimit.FIFTEEN)
			.image("http://이미지.jpg")
			.region(Region.SEOUL)
			.build();

		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));

		//when
		Product result = productService.getProductById(productId);

		Assertions.assertEquals(result.getId(), givenProduct.getId());
		Assertions.assertEquals(result.getName(), givenProduct.getName());

	}

}
