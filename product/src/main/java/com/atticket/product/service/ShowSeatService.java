package com.atticket.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atticket.product.domain.ReservedSeat;
import com.atticket.product.domain.Seat;
import com.atticket.product.domain.ShowSeat;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.dto.service.GetRemainSeatsSvcDto;
import com.atticket.product.repository.ShowSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowSeatService {

	private final ShowSeatRepository showSeatRepository;
	private final ReservedSeatService reservedSeatService;
	private final GradeService gradeService;
	private final SeatService seatService;

	public List<GetRemainSeatsSvcDto> getRemainSeatsByShowId(Long showId) {
		List<ShowSeat> showSeats = showSeatRepository.findShowSeatByShowId(showId);
		List<ReservedSeat> reservedSeats = reservedSeatService.getReservedSeatsByShowId(showId);

		return showSeats.stream().map(showSeat -> {
			List<Long> seatIdList = convertStringToList(showSeat.getSeatList());
			List<Long> reservedSeatIdList = reservedSeats.stream()
				.map(ReservedSeat::getSeatId)
				.collect(Collectors.toList());
			List<Long> remainSeatIdList = seatIdList.stream()
				.filter(seatId -> !reservedSeatIdList.contains(seatId))
				.collect(Collectors.toList());
			List<Seat> remainSeats = remainSeatIdList.stream()
				.map(seatService::getSeatById)
				.collect(Collectors.toList());
			return new GetRemainSeatsSvcDto(remainSeats, gradeService.getGradeById(showSeat.getGradeId()));
		}).collect(Collectors.toList());

	}

	/**
	 * 등급별 남은 좌석 조회
	 * @param showId
	 * @return
	 */
	public List<GetRemainSeatCntSvcDto> getRemainSeatCntByShowId(Long showId) {

		//공연의 좌석 - 등급 매핑 정보 조회
		List<ShowSeat> showSeats = showSeatRepository.findShowSeatByShowId(showId);
		//showId로 예매 좌석 리스트 조회
		List<ReservedSeat> reservedSeats = reservedSeatService.getReservedSeatsByShowId(showId);

		List<GetRemainSeatCntSvcDto> serviceDtoList = new ArrayList<>();

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
				GetRemainSeatCntSvcDto.builder()
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
	 * 공연 좌석 매핑 정보 등록
	 * @param productId
	 * @param showId
	 * @param gradeId
	 * @param seats
	 * @return
	 */
	public Long registerShowSeat(Long productId, Long showId, Long gradeId, List<Long> seats) {

		ShowSeat showSeat = ShowSeat.builder()
			.showId(showId)
			.gradeId(gradeId)
			.productId(productId)
			.seatList(convertListToString(seats))
			.build();

		return showSeatRepository.save(showSeat);
	}

	/**
	 * seatList을 String -> List<Long> 반환
	 * @param stringSeatList
	 * @return
	 */
	private List<Long> convertStringToList(String stringSeatList) {

		List<Long> seatList = new ArrayList<>();
		if (StringUtils.hasText(stringSeatList)) {
			String[] seatString = (stringSeatList).split(",");
			Arrays.stream(seatString).forEach(x -> seatList.add(Long.parseLong(x)));
		}
		return seatList;
	}

	/**
	 * seatList을 List<Long> -> String 반환
	 * @param seatList
	 * @return
	 */
	private String convertListToString(List<Long> seatList) {

		List<String> seatStringList = seatList.stream().map(s -> Long.toString(s)).collect(Collectors.toList());
		return String.join(",", seatStringList);
	}

}
