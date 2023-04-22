package com.atticket.product.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
	MUSICAL("뮤지컬", Arrays.asList(SubCategory.ORIGINAL, SubCategory.CREATIVE)),
	MOVIE("영화", Arrays.asList(SubCategory.HORROR, SubCategory.COMEDY, SubCategory.ROMANCE)),
	EMPTY("없음", Collections.EMPTY_LIST);

	private final String category;
	private final List<SubCategory> subCategories;

	/**
	 *서브카테고리로 카테고리 찾기
	 * @param subCategory
	 * @return Category
	 */
	public static Category findBySubCategory(SubCategory subCategory) {
		return Arrays.stream(Category.values())
			.filter(subList -> subList.hasSub(subCategory))
			.findAny()
			.orElse(EMPTY);
	}

	public boolean hasSub(SubCategory sub) {
		return subCategories.stream()
			.anyMatch(s -> s == sub);

	}
}
