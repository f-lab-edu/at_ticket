package com.atticket.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	// 테스트 데이터
	// private final CopyOnWriteArrayList<Place> placeTestDatas = new CopyOnWriteArrayList<>(new ArrayList<>());
	//
	// public PlaceRepository() {
	// 	for (long i = 1; i <= Region.values().length; i++) {
	// 		Region region = Region.values()[(int)(i - 1)];
	// 		placeTestDatas.add(
	// 			new Place(i, "장소" + i, "장소" + i + " 주소", "장소" + i + " 연락처", region));
	// 	}
	// }
	//
	// public Optional<Place> findById(Long id) {
	// 	return placeTestDatas.stream()
	// 		.filter(
	// 			place -> place.getId().equals(id)
	// 		).findAny();
	// }
}
