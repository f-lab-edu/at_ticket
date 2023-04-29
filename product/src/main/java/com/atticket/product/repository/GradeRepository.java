package com.atticket.product.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Grade;

@Repository
public class GradeRepository {

	private List<Grade> gradeTestDatas = new ArrayList<>(Arrays.asList(
		Grade.builder()
			.id(1L)
			.type("A")
			.price(5000)
			.productId(1L)
			.build(),
		Grade.builder()
			.id(2L)
			.type("B")
			.price(1000)
			.productId(1L)
			.build()

	));

	public Optional<Grade> findById(Long id) {

		return gradeTestDatas.stream()
			.filter(
				grade -> grade.getId().equals(id)
			).findAny();
	}

	public List<Grade> findGradeByProductId(Long productId) {

		return gradeTestDatas.stream()
			.filter(
				grade -> grade.getProductId().equals(productId)
			).collect(Collectors.toList());
	}

	public List<Grade> findAll() {
		return gradeTestDatas;
	}
}
