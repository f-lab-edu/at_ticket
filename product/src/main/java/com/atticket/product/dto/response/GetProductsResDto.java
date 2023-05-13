package com.atticket.product.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.atticket.product.domain.Place;
import com.atticket.product.domain.Product;
import com.atticket.product.type.Category;
import com.atticket.product.type.SubCategory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetProductsResDto {

	private final List<ProductDto> products;

	public static GetProductsResDto construct(List<Product> products) {
		if (CollectionUtils.isEmpty(products)) {
			return null;
		}
		return new GetProductsResDto(products.stream().map(ProductDto::construct).collect(Collectors.toList()));
	}

	@Builder
	@Getter
	private static class ProductDto {

		private final Category category;

		private final SubCategory subCategory;

		private final Long id;

		private final String name;

		private final String image;

		private final PlaceDto place;

		private final LocalDate startDate;

		private final LocalDate endDate;

		private final LocalTime runningTime;

		private final LocalTime interMission;

		private final String ageLimit;

		private static ProductDto construct(Product product) {
			return ProductDto.builder()
				.category(product.getCategory())
				.subCategory(product.getSubCategory())
				.id(product.getId())
				.name(product.getName())
				.image(product.getImage())
				.place(PlaceDto.construct(product.getPlace()))
				.startDate(product.getStartDate())
				.endDate(product.getEndDate())
				.runningTime(product.getRunningTime())
				.interMission(product.getInterMission())
				.ageLimit(product.getAgeLimit().getExplain())
				.build();
		}
	}

	@AllArgsConstructor
	@Getter
	private static class PlaceDto {

		private final Long id;

		private final String name;

		private final String region;

		private static PlaceDto construct(Place place) {
			return new PlaceDto(place.getId(), place.getName(), place.getRegion().getRegion());
		}
	}
}
