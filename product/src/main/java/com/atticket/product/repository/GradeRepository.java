package com.atticket.product.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Grade;

@Repository
public class GradeRepository {

	private final ProductRepository productRepository;

	private List<Grade> gradeTestDatas = new CopyOnWriteArrayList<>();

	// 생성시 테스트데이터 추가
	public GradeRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.gradeTestDatas.addAll(Arrays.asList(
			Grade.builder()
				.id(1L)
				.type("A")
				.price(5000)
				.product(productRepository.findById(1L).orElse(null))
				.build(),
			Grade.builder()
				.id(2L)
				.type("B")
				.price(1000)
				.product(productRepository.findById(1L).orElse(null))
				.build()
		));
	}

	public Optional<Grade> findById(Long id) {

		return gradeTestDatas.stream()
			.filter(
				grade -> grade.getId().equals(id)
			).findAny();
	}

	public List<Grade> findGradeByProductId(Long productId) {

		return gradeTestDatas.stream()
			.filter(
				grade -> grade.getProduct().getId().equals(productId)
			).collect(Collectors.toList());
	}
}
