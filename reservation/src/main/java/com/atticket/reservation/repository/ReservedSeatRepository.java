package com.atticket.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.reservation.domain.ReservedSeat;

@Repository
public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Long> {
	List<ReservedSeat> findByShowId(Long showId);

	Boolean existsByShowIdAndSeatIdIn(Long showId, List<Long> seatId);
}
