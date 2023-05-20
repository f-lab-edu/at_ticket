package com.atticket.product.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.ShowSeat;

@Repository
public class ShowSeatRepository {

	private final ShowRepository showRepository;
	private final SeatRepository seatRepository;
	private final GradeRepository gradeRepository;

	private List<ShowSeat> showSeatTestDatas = new CopyOnWriteArrayList<>();

	public ShowSeatRepository(ShowRepository showRepository, SeatRepository seatRepository,
		GradeRepository gradeRepository) {
		this.showRepository = showRepository;
		this.seatRepository = seatRepository;
		this.gradeRepository = gradeRepository;
		showSeatTestDatas.addAll(Arrays.asList(
			ShowSeat.builder()
				.id(1L)
				.show(showRepository.findById(1L).orElse(null))
				.seats(seatRepository.findByIdList(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L)))
				.grade(gradeRepository.findById(1L).orElse(null))
				.build(),
			ShowSeat.builder()
				.id(2L)
				.show(showRepository.findById(1L).orElse(null))
				.seats(seatRepository.findByIdList(List.of(8L, 9L, 10L, 11L)))
				.grade(gradeRepository.findById(2L).orElse(null))
				.build(),
			ShowSeat.builder()
				.id(3L)
				.show(showRepository.findById(2L).orElse(null))
				.seats(seatRepository.findByIdList(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)))
				.grade(gradeRepository.findById(1L).orElse(null))
				.build(),
			ShowSeat.builder()
				.id(4L)
				.show(showRepository.findById(2L).orElse(null))
				.seats(seatRepository.findByIdList(List.of(9L, 10L, 11L)))
				.grade(gradeRepository.findById(2L).orElse(null))
				.build()
		));
	}

	public Optional<ShowSeat> findById(String id) {
		return showSeatTestDatas.stream()
			.filter(
				showSeat -> showSeat.getId().equals(id)
			).findAny();
	}

	public List<ShowSeat> findShowSeatByProductId(Long productId) {

		return showSeatTestDatas.stream()
			.filter(
				showSeat -> showSeat.getShow().getProduct().getId().equals(productId)
			).collect(Collectors.toList());
	}

	public List<ShowSeat> findShowSeatByShowId(Long showId) {

		return showSeatTestDatas.stream()
			.filter(
				showSeat -> showSeat.getShow().getId().equals(showId)
			).collect(Collectors.toList());
	}

	public Long save(ShowSeat showSeat) {
		showSeat.setId((long)showSeatTestDatas.size() + 1);
		showSeatTestDatas.add(showSeat);
		return (long)showSeatTestDatas.size() + 1;
	}

}
