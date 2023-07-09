package com.atticket.reservation.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.atticket.reservation.dto.service.RegisterReservationSvcDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReservationReqDto {

	@NotNull
	private Long showId;

	@NotNull
	private List<Long> seatIds;

	public RegisterReservationSvcDto convert() {
		return RegisterReservationSvcDto.builder()
			.showId(this.showId)
			.seatIds(this.seatIds)
			.build();
	}
}
