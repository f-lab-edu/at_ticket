package com.atticket.product.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class GetSeatsInfoReqDto {

	private List<Long> seatIds;
}
