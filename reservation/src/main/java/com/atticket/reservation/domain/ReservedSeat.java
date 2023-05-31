package com.atticket.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "RESERVED_SEAT")
public class ReservedSeat {

	//예약좌석 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//공연 id
	private Long showId;

	//좌석 id
	private Long seatId;

}
