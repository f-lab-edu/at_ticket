package com.atticket.product.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.SubCategory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCT")
public class Product {

	//상품 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//이름
	private String name;

	//설명
	private String explains;

	//도메인 카테고리
	@Enumerated(EnumType.STRING)
	private Category category;

	//도메인별 세부 장르
	@Enumerated(EnumType.STRING)
	private SubCategory subCategory;

	//러닝 타임
	private LocalTime runningTime;

	//인터 미션
	private LocalTime interMission;

	//상연 시작 일자
	private LocalDate startDate;

	//상연 종료 일자
	private LocalDate endDate;

	//이미지
	private String image;

	//나이 제한
	@Enumerated(EnumType.STRING)
	private AgeLimit ageLimit;

	//장소 Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

}
