package com.atticket.reservation.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "RESERVATION")
public class Reservation {

	//예약 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//유저 id
	private Long userId;

	//좌석 Id
	private String seatId;

	//상태
	private Status status;

	//예약 시간
	private LocalDateTime time;

	//공연 id
	private Long showId;

	//좌석 등급 id
	private Long gradeId;

	//가격
	private int price;

}
