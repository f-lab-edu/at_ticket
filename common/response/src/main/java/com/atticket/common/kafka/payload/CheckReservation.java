package com.atticket.common.kafka.payload;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckReservation implements Serializable {

	private Long showId;

	private List<Long> seatIds;

	private String userId;

}
