package com.atticket.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode
@IdClass(ReservedSeatId.class)
@Table(name = "RESERVED_SEAT")
public class ReservedSeat {

	//공연 id
	@Id
	private Long showId;

	//좌석 id
	@Id
	private Long seatId;

}
