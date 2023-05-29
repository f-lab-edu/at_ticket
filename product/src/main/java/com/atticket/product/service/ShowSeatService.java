package com.atticket.product.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Seat;
import com.atticket.product.domain.Show;
import com.atticket.product.domain.ShowSeat;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.dto.service.GetRemainSeatsSvcDto;
import com.atticket.product.dto.service.RegisterShowServiceDto;
import com.atticket.product.repository.ShowSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowSeatService {

	// service
	private final ShowService showService;
	private final GradeService gradeService;
	private final SeatService seatService;
	private final ReservedSeatService reservedSeatService;

	// repository
	private final ShowSeatRepository showSeatRepository;

	/**
	 * 공연의 남은 좌석 조회
	 * */
	public List<GetRemainSeatsSvcDto> getRemainSeatsByShowId(Long showId) {
		// ShowSeat(공연 등급별 좌석 리스트) 리스트
		List<ShowSeat> showSeats = showSeatRepository.findShowSeatByShowId(showId);
		// 예약된 좌석 id 리스트
		List<Long> reservedSeatIdList = reservedSeatService.getReservedSeatIdsByShowId(showId);

		return showSeats.stream().map(showSeat -> {
			List<Long> seatIdList = showSeat.getSeats().stream().map(Seat::getId).collect(Collectors.toList());
			List<Long> remainSeatIdList = seatIdList.stream()
				.filter(seatId -> !reservedSeatIdList.contains(seatId))
				.collect(Collectors.toList());
			List<Seat> remainSeats = remainSeatIdList.stream()
				.map(seatService::getSeatById)
				.collect(Collectors.toList());
			return new GetRemainSeatsSvcDto(remainSeats, showSeat.getGrade());
		}).collect(Collectors.toList());

	}

	/**
	 * 등급별 남은 좌석수 조회
	 * @param showId
	 * @return
	 */
	public List<GetRemainSeatCntSvcDto> getRemainSeatCntByShowId(Long showId) {

		//공연의 좌석 - 등급 매핑 정보 조회
		List<ShowSeat> showSeats = showSeatRepository.findShowSeatByShowId(showId);
		//showId로 예매 좌석 리스트 조회
		List<Long> reservedSeatIds = reservedSeatService.getReservedSeatIdsByShowId(showId);

		List<GetRemainSeatCntSvcDto> serviceDtoList = new ArrayList<>();

		//등급별 남은 좌석 :showSeats  등급별 좌석  -  예매 좌석
		for (ShowSeat showSeat : showSeats) {
			List<Long> seats = showSeat.getSeats().stream().map(Seat::getId).collect(Collectors.toList());
			int remainSeatCnt = seats.size();

			for (Long reservedSeat : reservedSeatIds) {
				for (Long seat : seats) {
					if (seat.equals(reservedSeat)) {
						remainSeatCnt = remainSeatCnt - 1;
					}
				}
			}

			serviceDtoList.add(
				GetRemainSeatCntSvcDto.builder()
					.showId(showId)
					.gradeId(showSeat.getGrade().getId())
					.gradeNm(showSeat.getGrade().getType())
					.seatCnt(remainSeatCnt)
					.build()
			);
		}

		return serviceDtoList;
	}

	/**
	 * 공연 좌석 매핑 정보 등록
	 * @param showId
	 * @param gradeId
	 * @param seatIds
	 * @return
	 */
	public Long registerShowSeat(Long showId, Long gradeId, List<Long> seatIds) {

		Show show = showService.getShowById(showId);
		if (Objects.isNull(show)) {
			throw new BaseException(BaseStatus.INVALID_SHOW);
		}

		Grade grade = gradeService.getGradeById(gradeId);
		if (Objects.isNull(grade)) {
			throw new BaseException(BaseStatus.INVALID_GRADE);
		}

		// 해당 공연에 같은 등급의 좌석정보가 이미 존재하면 exception
		if (showSeatRepository.isExistByShowIdAndGradeId(showId, gradeId)) {
			throw new BaseException(BaseStatus.ALREADY_EXIST_SHOW_SEAT);
		}

		Set<Long> idSet = new HashSet<>(seatIds);
		// 중복된 좌석 id가 있으면 exception return
		if (seatIds.size() != idSet.size()) {
			throw new BaseException(BaseStatus.NO_DUPLICATE_SEAT);
		}
		List<Seat> seats = seatService.getSeatsByIdList(seatIds);
		// 유효하지 않은 좌석 id가 있으면 exception return
		if (seatIds.size() != seats.size()) {
			throw new BaseException(BaseStatus.INVALID_SEAT);
		}

		// 홀에 속한 좌석이 아니면 exception
		seats.forEach(seat -> {
			if (!seat.getHall().equals(show.getHall())) {
				throw new BaseException(BaseStatus.HALL_DOES_NOT_INCLUDE_SEAT);
			}
		});

		ShowSeat showSeat = ShowSeat.builder()
			.show(show)
			.grade(grade)
			.seats(seats)
			.build();

		return showSeatRepository.save(showSeat);
	}

	public List<Long> registerShow(Long productId, RegisterShowServiceDto registerShowServiceDto) {
		return registerShowServiceDto.getShowInfos().stream().map(showInfo -> {
			Long showId = showService.saveShow(productId, showInfo.getDate(),
				showInfo.getTime(), showInfo.getHallId(), showInfo.getSession());
			//등록된 공연의 좌석-등급 매핑 저장
			showInfo.getSeatInfos()
				.forEach(seatInfo -> registerShowSeat(showId, seatInfo.getGradeId(), seatInfo.getSeatIds()));
			return showId;
		}).collect(Collectors.toList());
	}
}
