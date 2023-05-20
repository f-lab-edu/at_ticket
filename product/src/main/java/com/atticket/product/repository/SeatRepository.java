package com.atticket.product.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Seat;

@Repository
public class SeatRepository {

	private final HallRepository hallRepository;

	private List<Seat> seatTestDatas = new CopyOnWriteArrayList<>();

	public SeatRepository(HallRepository hallRepository) {
		this.hallRepository = hallRepository;
		for (long i = 0; i < 30; i++) {
			seatTestDatas.add(
				Seat.builder()
					.id(i)
					.space("1층")
					.locX(String.valueOf(i * 10))
					.locY(String.valueOf(i * 10))
					.row("A")
					.rowNum((int)(i + 1))
					.hall(hallRepository.findById(1L).orElse(null))
					.build()
			);
		}
	}

	public Optional<Seat> findById(Long seatId) {

		return seatTestDatas.stream()
			.filter(
				seat -> seat.getId().equals(seatId)
			).findAny();
	}

	public List<Seat> findByIdList(List<Long> seatIdList) {
		return seatTestDatas.stream()
			.filter(
				seat -> seatIdList.contains(seat.getId())
			).collect(Collectors.toList());
	}

}
