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

	// service
	private final ShowSeatService showSeatService;
	private final HallService hallService;
	private final ProductService productService;

	// repository
	private final ShowRepository showRepository;

	public Show getShowById(Long id) {
		return showRepository.findById(id).orElse(null);
	}

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

		LocalDate parsedDate;
		LocalTime parsedTime;
		Long registeredShowId = null;

		try {
			parsedDate = LocalDate.parse(registerShowDto.getDate(), DateTimeFormatter.BASIC_ISO_DATE);
			parsedTime = LocalTime.parse(registerShowDto.getTime(), DateTimeFormatter.ISO_LOCAL_TIME);
		} catch (Exception e) {
			throw new BaseException(BaseStatus.UNEXPECTED_ERROR);
		}

		Show show = Show.builder()
			.product(productService.getProductById(productId))
			.date(parsedDate)
			.time(parsedTime)
			.hall(hallService.getHallById(Long.parseLong(registerShowDto.getHallId())))
			.session(Integer.parseInt(registerShowDto.getSession()))
			.build();

		//공연 저장
		registeredShowId = showRepository.save(show, productId);

		//등록된 공연의 좌석-등급 매핑 저장
		if (Objects.isNull(registeredShowId)) {
			throw new BaseException(BaseStatus.UNEXPECTED_ERROR);
		} else {

			List<RegisterShowServiceDto.SeatInfo> seats = registerShowDto.getSeats();
			for (int i = 0; i < seats.size(); i++) {
				RegisterShowServiceDto.SeatInfo seat = seats.get(i);
				showSeatService.registerShowSeat(show, Long.valueOf(seat.getGrade()),
					seat.getIds().stream().map(Long::parseLong).collect(Collectors.toList()));
			}

		}

		return registeredShowId;
	}

}
