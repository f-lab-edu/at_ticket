package com.atticket.reservation.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.atticket.reservation.domain.converter.ReservationEntitySeatIdsConverter;
import com.atticket.reservation.type.Status;

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
@Table(name = "reservation")
public class Reservation {

	//예약 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//유저 id
	private String userId;

	//좌석 Id
	@Column(length = 1000)
	@Convert(converter = ReservationEntitySeatIdsConverter.class)
	private List<Long> seatIds;

	//상태
	@Enumerated(EnumType.STRING)
	private Status status;

	//예약 시간
	private LocalDateTime time;

	//공연 id
	private Long showId;

	public void reserve() {
		this.status = Status.RESERVED;
	}

}
