package com.atticket.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Show;
import com.atticket.product.domain.ShowSeat;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

	ShowSeat findByShowId_idAndGrade_id(Long showId, Long gradeId);

	List<ShowSeat> findByShowId_id(Long showId);

	List<ShowSeat> findByProduct_idAndHall_id(Long productId, Long hallId);

	int deleteByShowId(Show show);
}
