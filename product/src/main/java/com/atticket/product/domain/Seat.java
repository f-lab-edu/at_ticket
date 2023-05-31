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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SEAT")
public class Seat {

	//좌석 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//공간 (e.g. 1층, 2층, .../ A존, B존, ...)
	private String space;

	//x 좌표
	private String locX;

	//y 좌표
	private String locY;

	//행
	private String seatRow;

	//행 번호
	private int rowNum;

	//공연홀
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id")
	private Hall hall;
}
