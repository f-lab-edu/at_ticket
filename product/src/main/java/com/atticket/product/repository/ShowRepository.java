package com.atticket.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

	List<Show> findByProduct_id(Long productId);

	public int deleteByProduct(Product product);
}
