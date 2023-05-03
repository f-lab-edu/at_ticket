package com.atticket.product.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Show;
import com.atticket.product.dto.service.RegisterShowServiceDto;
import com.atticket.product.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {

	private final ShowRepository showRepository;
	private final ShowSeatService showSeatService;

	/**
	 * 상품의 공연 정보 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<Show> getShowsByProductId(Long productId) {
		return showRepository.findShowsByProductId(productId);
	}

	/**
	 * 상품의 공연 날짜 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<LocalDate> getShowDatesByProductId(Long productId) {
		return showRepository.findShowsByProductId(productId)
			.stream()
			.map(Show::getDate)
			.collect(Collectors.toList());
	}

	/**
	 * 특정 날짜의 공연 리스트 조회
	 * @param productId
	 * @param date
	 * @return
	 */
	public List<Show> getShowDateByProductId(Long productId, LocalDate date) {
		return showRepository.findShowsByProductId(productId)
			.stream()
			.filter(show -> show.getDate().equals(date))
			.collect(Collectors.toList());
	}

	/**
	 * 공연 등록
	 * @param productId
	 * @param registerShowDto
	 * @return 등록된 showId
	 */
	public Long registerShow(long productId, RegisterShowServiceDto registerShowDto) {

		LocalDate paredDate;
		LocalTime paredtime;
		Long registedShowId = null;

		try {
			paredDate = LocalDate.parse(registerShowDto.getDate(), DateTimeFormatter.BASIC_ISO_DATE);
			paredtime = LocalTime.parse(registerShowDto.getTime(), DateTimeFormatter.ISO_LOCAL_TIME);
		} catch (Exception e) {
			throw new BaseException(BaseStatus.UNEXPECTED_ERROR);
		}

		Show show = Show.builder()
			.productId(productId)
			.date(paredDate)
			.time(paredtime)
			.hallId(Long.valueOf(registerShowDto.getHallId()))
			.session(Integer.parseInt(registerShowDto.getSession()))
			.hallId(Long.parseLong(registerShowDto.getHallId()))
			.build();

		//공연 저장
		registedShowId = showRepository.save(show, productId);

		//등록된 공연의 좌석-등급 매핑 저장
		if (Objects.isNull(registedShowId)) {
			throw new BaseException(BaseStatus.UNEXPECTED_ERROR);
		} else {

			List<RegisterShowServiceDto.SeatInfo> seats = registerShowDto.getSeats();
			for (int i = 0; i < seats.size(); i++) {
				RegisterShowServiceDto.SeatInfo seat = seats.get(i);
				showSeatService.registerShowSeat(productId, registedShowId, Long.valueOf(seat.getGrade()),
					seat.getIds().stream().map(Long::parseLong).collect(Collectors.toList()));
			}

		}

		return registedShowId;
	}

}
