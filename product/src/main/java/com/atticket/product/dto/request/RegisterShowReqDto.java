package com.atticket.product.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import com.atticket.common.utils.Constants;
import com.atticket.product.dto.service.RegisterShowServiceDto;

import lombok.Getter;

@Getter
public class RegisterShowReqDto {

	//공연 시간/좌석 정보 리스트
	@Valid
	private List<ShowInfo> shows;

	//공연 시간/좌석 정보
	@Getter
	public static class ShowInfo {

		//날짜
		@DateTimeFormat(pattern = Constants.DATE_PATTERN)
		private LocalDate date;

		//시간
		@DateTimeFormat(pattern = Constants.TIME_PATTERN)
		private LocalTime time;

		//회차
		@Min(value = 1)
		private int session;

		//공연 홀 id
		private Long hallId;

		//좌석 등급 매핑 정보 (좌석ID, 등급)
		private List<SeatInfo> seats;

	}

	@Getter
	public static class SeatInfo {

		//좌석 등급
		private Long grade;

		//좌석 Id
		private List<Long> ids;

		public void setIds(String ids) {
			this.ids = Arrays.stream(ids.split(","))
				.map(id -> Long.parseLong(id.trim()))
				.collect(Collectors.toList());
		}
	}

	public RegisterShowServiceDto convert() {
		return RegisterShowServiceDto.builder()
			.showInfos(this.shows.stream().map(show ->
				RegisterShowServiceDto.ShowInfo.builder()
					.date(show.getDate())
					.time(show.getTime())
					.session(show.getSession())
					.hallId(show.getHallId())
					.seatInfos(show.getSeats().stream().map(seat ->
						RegisterShowServiceDto.SeatInfo.builder()
							.gradeId(seat.getGrade())
							.seatIds(seat.getIds())
							.build())
						.collect(Collectors.toList()))
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
