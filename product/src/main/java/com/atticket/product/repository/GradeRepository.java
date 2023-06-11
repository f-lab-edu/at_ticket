package com.atticket.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Product;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
	public List<Grade> findByProduct_id(Long productId);

	public int deleteByProduct(Product product);
}
