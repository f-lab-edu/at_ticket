package com.atticket.product.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Product;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

@Repository
public class ProductRepository {

	/// 테스트 데이타
	private List<Product> productTestDatas = Collections.synchronizedList(new ArrayList<>(
		Arrays.asList(
			Product.builder()
				.id(1L)
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
				.build(),
			Product.builder()
				.id(2L)
				.category(Category.MUSICAL)
				.subCategory(SubCategory.ORIGINAL)
				.name("상품2")
				.explain("설명")
				.runningTime(LocalTime.of(2, 0))
				.startDate(LocalDate.of(2023, 4, 21))
				.endDate(LocalDate.of(2024, 4, 21))
				.ageLimit(AgeLimit.FIFTEEN)
				.image("http://이미지.jpg")
				.region(Region.SEOUL)
				.build()

		))
	);

	public Long save(Product product) {

		product.setId((long)(productTestDatas.size() + 1));
		productTestDatas.add(product);

		return (long)productTestDatas.size() + 1;
	}

	public Optional<Product> findById(Long id) {

		return productTestDatas.stream()
			.filter(
				product -> product.getId().equals(id)
			).findAny();
	}

	public void deleteById(Long id) {
		productTestDatas.removeIf(product -> id.equals(product.getId()));
	}

}
