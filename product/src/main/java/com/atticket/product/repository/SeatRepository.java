package com.atticket.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

	Optional<Seat> findBySeatNo(Long seatNo);

	Optional<Seat> findBySeatNoAndHallId(Long seatNo, Long hallId);

}
