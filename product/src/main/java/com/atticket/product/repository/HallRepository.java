package com.atticket.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

	// private final PlaceRepository placeRepository;
	//
	// // 테스트 데이터
	// private final CopyOnWriteArrayList<Hall> hallTestDatas = new CopyOnWriteArrayList<>(new ArrayList<>());
	//
	// public HallRepository(PlaceRepository placeRepository) {
	// 	this.placeRepository = placeRepository;
	// 	for (long i = 1; i < +Region.values().length; i++) {
	// 		hallTestDatas.addAll(List.of(
	// 				Hall.builder()
	// 					.id(2 * i - 1)
	// 					.name("홀 " + (2 * i - 1))
	// 					.place(placeRepository.findById(i).orElse(null))
	// 					.build(),
	// 				Hall.builder()
	// 					.id(2 * i)
	// 					.name("홀 " + (2 * i))
	// 					.place(placeRepository.findById(i).orElse(null))
	// 					.build()
	// 			)
	// 		);
	// 	}
	// }
	//
	// public Optional<Hall> findById(Long id) {
	// 	return hallTestDatas.stream()
	// 		.filter(
	// 			hall -> hall.getId().equals(id)
	// 		).findAny();
	// }
}
