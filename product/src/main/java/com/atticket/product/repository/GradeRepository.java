package com.atticket.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

	public List<Grade> findByProduct_id(Long productId);

	// private final ProductRepository productRepository;
	//
	// private List<Grade> gradeTestDatas = new CopyOnWriteArrayList<>();
	//
	// // 생성시 테스트데이터 추가
	// public GradeRepository(ProductRepository productRepository) {
	// 	this.productRepository = productRepository;
	// 	this.gradeTestDatas.addAll(Arrays.asList(
	// 		Grade.builder()
	// 			.id(1L)
	// 			.type("A")
	// 			.price(5000)
	// 			.product(productRepository.findById(1L).orElse(null))
	// 			.build(),
	// 		Grade.builder()
	// 			.id(2L)
	// 			.type("B")
	// 			.price(1000)
	// 			.product(productRepository.findById(1L).orElse(null))
	// 			.build()
	// 	));
	// }

}
