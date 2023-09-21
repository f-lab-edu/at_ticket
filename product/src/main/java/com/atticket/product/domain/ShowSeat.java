package com.atticket.product.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 좌석 -등급 매핑
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SHOW_SEAT")
public class ShowSeat {

	//공연좌석 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//공연
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "show_id")
	private Show showId;

	//좌석 리스트
	private String seats;

	//등급 id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id")
	private Grade grade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id")
	private Hall hall;

}
