package com.atticket.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
