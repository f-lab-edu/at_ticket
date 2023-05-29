package com.atticket.product.domain;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

/**
 * 좌석 -등급 매핑
 */
@Getter
@Builder
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

	//Todo: 수정필요
	//좌석 리스트
	@Convert(converter = ShowSeatConverter.class)
	private List<Seat> seats;

	//등급 id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id")
	private Grade grade;

}
