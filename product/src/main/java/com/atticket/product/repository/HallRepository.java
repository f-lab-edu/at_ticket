package com.atticket.product.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Hall;
import com.atticket.product.type.Region;

@Repository
public class HallRepository {

	private final PlaceRepository placeRepository;

	// 테스트 데이터
	private final CopyOnWriteArrayList<Hall> hallTestDatas = new CopyOnWriteArrayList<>(new ArrayList<>());

	public HallRepository(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
		for (long i = 1; i < +Region.values().length; i++) {
			hallTestDatas.addAll(List.of(
				Hall.builder()
					.id(2 * i - 1)
					.name("홀 " + (2 * i - 1))
					.place(placeRepository.findById(i).orElse(null))
					.build(),
				Hall.builder()
					.id(2 * i)
					.name("홀 " + (2 * i))
					.place(placeRepository.findById(i).orElse(null))
					.build()
				)
			);
		}
	}

	public Optional<Hall> findById(Long id) {
		return hallTestDatas.stream()
			.filter(
				hall -> hall.getId().equals(id)
			).findAny();
	}
}
