package com.atticket.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atticket.reservation.domain.PreReservedSeat;

public interface PreReservedSeatRepository extends JpaRepository<PreReservedSeat, Long> {

	Boolean existsByShowIdAndSeatIdIn(Long showId, List<Long> seatId);

	Boolean existsByShowIdAndSeatIdInAndUserIdNot(Long showId, List<Long> seatId, String userId);

	List<PreReservedSeat> findByShowIdAndSeatIdIn(Long showId, List<Long> seatId);

	List<PreReservedSeat> findByShowId(Long showId);
}
