package com.atticket.product.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Show;

@Repository
public class ShowRepository {

	private List<Show> showTestDatas = new CopyOnWriteArrayList<>(Arrays.asList(

		Show.builder()
			.id(1L)
			.time(LocalTime.of(10, 0, 0))
			.session(1)
			.date(LocalDate.of(2023, 3, 1))
			.productId(1L)
			.build(),
		Show.builder()
			.id(2L)
			.time(LocalTime.of(12, 0, 0))
			.session(2)
			.date(LocalDate.of(2023, 3, 1))
			.productId(1L)
			.build(),
		Show.builder()
			.id(3L)
			.time(LocalTime.of(10, 0, 0))
			.session(1)
			.date(LocalDate.of(2023, 4, 1))
			.productId(1L)
			.build()
	));

	public Long save(Show show, Long productId) {

		show.setId((long)(showTestDatas).size() + 1);
		showTestDatas.add(show);

		return (long)showTestDatas.size() + 1;
	}

	public Optional<Show> findById(String id) {

		return showTestDatas.stream()
			.filter(
				show -> show.getId().equals(id)
			).findAny();
	}

	public List<Show> findShowsByProductId(Long productId) {

		return showTestDatas.stream()
			.filter(
				show -> show.getProductId().equals(productId)
			).collect(Collectors.toList());
	}

}
