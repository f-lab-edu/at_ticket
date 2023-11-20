package com.atticket.reservation.repository;

import com.atticket.reservation.domain.PreReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface PreReservedSeatRepository extends JpaRepository<PreReservedSeat, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
//	@Cacheable(value = "getPreReservationSeats", key = "{#showId, #seatId}")
	Boolean existsByShowIdAndSeatIdIn(Long showId, List<Long> seatId);

	Boolean existsByShowIdAndSeatIdInAndUserIdNot(Long showId, List<Long> seatId, String userId);

	List<PreReservedSeat> findByShowIdAndSeatIdIn(Long showId, List<Long> seatId);

	List<PreReservedSeat> findByShowId(Long showId);
}
