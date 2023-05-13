package com.atticket.product.type;

import static org.springframework.util.StringUtils.hasText;

import java.util.Arrays;
import java.util.List;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {

	MUSICAL("뮤지컬", Arrays.asList(SubCategory.ORIGINAL, SubCategory.CREATIVE)),
	MOVIE("영화", Arrays.asList(SubCategory.HORROR, SubCategory.COMEDY, SubCategory.ROMANCE));

	private final String category;

	private final List<SubCategory> subCategories;

	/**
	 *서브카테고리로 카테고리 찾기
	 * @param subCategory 서브카테고리
	 * @return Category
	 */
	public static Category findBySubCategory(SubCategory subCategory) {
		return Arrays.stream(Category.values())
			.filter(subList -> subList.hasSub(subCategory)).findAny().orElse(null);
	}

	public boolean hasSub(SubCategory sub) {
		return subCategories.stream()
			.anyMatch(s -> s == sub);
	}

	public static Category findByName(String name) {
		if (!hasText(name)) {
			return null;
		}

		return Arrays.stream(Category.values())
			.filter(category -> category.name().equals(name))
			.findAny().orElseThrow(() -> new BaseException(BaseStatus.INVALID_CATEGORY));
	}
}
