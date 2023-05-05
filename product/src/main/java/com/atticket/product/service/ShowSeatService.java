package com.atticket.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.ReservedSeat;
import com.atticket.product.domain.ShowSeat;
import com.atticket.product.dto.service.RemainSeatCntServiceDto;
import com.atticket.product.repository.ShowSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowSeatService {

	private final ShowSeatRepository showSeatRepository;
	private final ReservedSeatService reservedSeatService;
	private final GradeService gradeService;

	public List<ShowSeat> getShowSeatsByShowId(Long showId) {
		return showSeatRepository.findShowSeatByShowId(showId);
	}

	/**
	 * 등급별 남은 좌석 조회
	 * @param showId
	 * @return
	 */
	public List<RemainSeatCntServiceDto> getRemainSeatCntByShowId(Long showId) {

		//공연의 좌석 - 등급 매핑 정보 조회
		List<ShowSeat> showSeats = showSeatRepository.findShowSeatByShowId(showId);
		//showId로 예매 좌석 리스트 조회
		List<ReservedSeat> reservedSeats = reservedSeatService.getReservedSeatsByShowId(showId);

		List<RemainSeatCntServiceDto> serviceDtoList = new ArrayList<>();

		//등급별 남은 좌석 :showSeats  등급별 좌석  -  예매 좌석
		for (ShowSeat showSeat : showSeats) {
			List<Long> seats = convertStringToList(showSeat.getSeatList());
			int remainSeatCnt = seats.size();

			for (ReservedSeat reservedSeat : reservedSeats) {
				for (Long seat : seats) {
					if (seat.equals(reservedSeat.getSeatId())) {
						remainSeatCnt = remainSeatCnt - 1;
					}
				}
			}
			serviceDtoList.add(
				RemainSeatCntServiceDto.builder()
					.showId(showId)
					.gradeId(showSeat.getGradeId())
					.gradeNm(gradeService.getGradeNmById(showSeat.getGradeId()))
					.seatCnt(remainSeatCnt)
					.build()
			);
		}

		return serviceDtoList;
	}

	/**
	 * seatList을 String -> List<Long> 반환
	 * @param stringSeatList
	 * @return
	 */
	public List<Long> convertStringToList(String stringSeatList) {

		List<Long> seatList = new ArrayList<>();
		if (!stringSeatList.isBlank()) {
			String[] seatString = (stringSeatList).split(",");
			for (String seat : seatString) {
				seatList.add(Long.parseLong(seat));
			}
		}

		return seatList;
	}

	/**
	 * seatList을 List<Long> -> String 반환
	 * @param seatList
	 * @return
	 */
	public String convertListToString(List<Long> seatList) {

		List<String> seatStringList = seatList.stream().map(s -> Long.toString(s)).collect(Collectors.toList());
		return String.join(",", seatStringList);
	}

}
