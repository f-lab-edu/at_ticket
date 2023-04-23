package com.atticket.product.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.atticket.product.domain.Product;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

@SpringBootTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepo;

	@Test
	@DisplayName("ProductRepository Id로 테스트 데이터 조회 테스트")
	void findById() {

		//Given
		Long productId = 1L;

		//when
		Product result = productRepo.findById(productId).get();

		//then
		assertThat(result.getName()).isEqualTo("상품1");

	}

	@Test
	@DisplayName("ProductRepository Id로 테스트 데이터 조회 테스트 2")
	void findById2() {

		//Given
		Long productId = 3L;

		Product testProduct = Product.builder()
			.category(Category.MUSICAL)
			.subCategory(SubCategory.ORIGINAL)
			.name("상품3")
			.explain("설명")
			.runningTime(LocalTime.of(2, 0))
			.startDate(LocalDate.of(2023, 4, 21))
			.endDate(LocalDate.of(2024, 4, 21))
			.ageLimit(AgeLimit.FIFTEEN)
			.image("http://이미지.jpg")
			.region(Region.SEOUL)
			.build();

		productRepo.save(testProduct);

		//when
		Product result = productRepo.findById(productId).get();

		//then
		assertThat(result).isEqualTo(testProduct);

	}

}
