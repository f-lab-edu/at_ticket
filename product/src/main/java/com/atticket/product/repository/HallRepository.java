package com.atticket.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}
