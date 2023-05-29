package com.atticket.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// private final PlaceRepository placeRepository;
	//
	// // 테스트 데이터
	// private final CopyOnWriteArrayList<Product> productTestDatas = new CopyOnWriteArrayList<>(new ArrayList<>());
	//
	// // 생성자에서 더미데이터 추가
	// public ProductRepository(PlaceRepository placeRepository) {
	// 	this.placeRepository = placeRepository;
	// 	long size = 30;
	// 	for (long i = 1; i <= size; i++) {
	// 		// category size
	// 		long cs = (long)Math.ceil((double)size / Category.values().length);
	// 		Category category = Category.values()[(int)Math.ceil((double)i / cs) - 1];
	// 		// subCategory size
	// 		long scs = (long)Math.ceil((double)cs / category.getSubCategories().size());
	// 		SubCategory subCategory = category.getSubCategories()
	// 			.get((int)Math.ceil((double)(i > cs ? i - cs : i) / scs) - 1);
	// 		// region size
	// 		long rs = (long)Math.ceil((double)size / Region.values().length);
	// 		Place place = placeRepository.findById((long)Math.ceil((double)i / rs)).orElse(null);
	// 		// ageLimit size
	// 		long as = (long)Math.ceil((double)size / AgeLimit.values().length);
	// 		AgeLimit ageLimit = AgeLimit.values()[(int)Math.ceil((double)as / rs) - 1];
	//
	// 		productTestDatas.add(new Product(i, "상품" + i, "상품" + i + " 설명", category,
	// 			subCategory, LocalTime.of(2, 0), LocalTime.of(0, 20),
	// 			LocalDate.of(2023, 4, 21), LocalDate.of(2023, 5, 21),
	// 			"https://상품" + i + " 이미지.jpg", ageLimit, place));
	// 	}
	// }
	//
	// public Long save(Product product) {
	//
	// 	product.setId((long)(productTestDatas.size() + 1));
	// 	productTestDatas.add(product);
	//
	// 	return (long)productTestDatas.size() + 1;
	// }
	//
	// public Optional<Product> findById(Long id) {
	//
	// 	return productTestDatas.stream()
	// 		.filter(
	// 			product -> product.getId().equals(id)
	// 		).findAny();
	// }
	//
	// public void deleteById(Long id) {
	// 	productTestDatas.removeIf(product -> id.equals(product.getId()));
	// }
	//
	// public List<Product> find(int page, int perPage, String keyword, Category category,
	// 	SubCategory subCategory, Region region, LocalDate startDate, LocalDate endDate, SortOption sortOption) {
	//
	// 	return productTestDatas.stream()
	// 		.filter(product -> (!StringUtils.hasText(keyword) || (product.getName().contains(keyword)))
	// 			&& (Objects.isNull(category) || product.getCategory().equals(category))
	// 			&& (Objects.isNull(subCategory) || product.getSubCategory().equals(subCategory))
	// 			&& (Objects.isNull(region) || product.getPlace().getRegion().equals(region))
	// 			&& (Objects.isNull(startDate) || product.getEndDate().compareTo(startDate) >= 0)
	// 			&& (Objects.isNull(endDate) || product.getStartDate().compareTo(endDate) <= 0)
	// 		)
	// 		.skip((long)(page - 1) * perPage).limit(perPage)
	// 		.collect(Collectors.toList());
	// }

}
