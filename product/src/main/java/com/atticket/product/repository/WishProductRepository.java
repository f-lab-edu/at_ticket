package com.atticket.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.WishProduct;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {
	public List<WishProduct> findByProductId(Long productId);
}
