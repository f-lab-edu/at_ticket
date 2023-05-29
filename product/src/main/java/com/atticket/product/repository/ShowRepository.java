package com.atticket.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

	public List<Show> findByProduct_id(Long productId);

	// private final ProductRepository productRepository;
	// private final HallRepository hallRepository;
	//
	// private List<Show> showTestDatas = new CopyOnWriteArrayList<>();
	//
	// public ShowRepository(ProductRepository productRepository, HallRepository hallRepository) {
	// 	this.productRepository = productRepository;
	// 	this.hallRepository = hallRepository;
	// 	this.showTestDatas.addAll(Arrays.asList(
	//
	// 		Show.builder()
	// 			.id(1L)
	// 			.time(LocalTime.of(10, 0, 0))
	// 			.session(1)
	// 			.date(LocalDate.of(2023, 3, 1))
	// 			.product(productRepository.findById(1L).orElse(null))
	// 			.hall(hallRepository.findById(1L).orElse(null))
	// 			.build(),
	// 		Show.builder()
	// 			.id(2L)
	// 			.time(LocalTime.of(12, 0, 0))
	// 			.session(2)
	// 			.date(LocalDate.of(2023, 3, 1))
	// 			.product(productRepository.findById(1L).orElse(null))
	// 			.hall(hallRepository.findById(1L).orElse(null))
	// 			.build(),
	// 		Show.builder()
	// 			.id(3L)
	// 			.time(LocalTime.of(10, 0, 0))
	// 			.session(1)
	// 			.date(LocalDate.of(2023, 4, 1))
	// 			.product(productRepository.findById(1L).orElse(null))
	// 			.hall(hallRepository.findById(1L).orElse(null))
	// 			.build()
	// 	));
	// }
	//
	// public Long save(Show show) {
	//
	// 	Long showId = (long)(showTestDatas).size() + 1;
	//
	// 	show.setId(showId);
	// 	showTestDatas.add(show);
	//
	// 	return showId;
	// }
	//
	// public Optional<Show> findById(Long id) {
	//
	// 	return showTestDatas.stream()
	// 		.filter(
	// 			show -> show.getId().equals(id)
	// 		).findAny();
	// }
	//
	// public List<Show> findShowsByProductId(Long productId) {
	//
	// 	return showTestDatas.stream()
	// 		.filter(
	// 			show -> show.getProduct().getId().equals(productId)
	// 		).collect(Collectors.toList());
	// }

}
