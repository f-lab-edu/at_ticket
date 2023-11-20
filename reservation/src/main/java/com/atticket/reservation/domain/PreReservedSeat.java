package com.atticket.reservation.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode
@IdClass(ReservedSeatId.class)
@Table(name = "PRE_RESERVED_SEAT_PK_TEST")
public class PreReservedSeat implements Serializable {

	//공연 id
	@Id
	private Long showId;

	//좌석 id
	@Id
	private Long seatId;

	//유저 id
	private String userId;

	//예약 시간
	private LocalDateTime time;
}
