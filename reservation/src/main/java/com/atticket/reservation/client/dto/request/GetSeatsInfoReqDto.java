package com.atticket.reservation.client.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSeatsInfoReqDto {

	private List<Long> seatIds;
}
