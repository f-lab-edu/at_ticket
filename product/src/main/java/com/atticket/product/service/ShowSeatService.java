package com.atticket.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atticket.product.client.client.ReservationFeignClient;
import com.atticket.product.domain.ReservedSeat;
import com.atticket.product.domain.Seat;
import com.atticket.product.domain.Show;
import com.atticket.product.domain.ShowSeat;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.dto.service.GetRemainSeatsSvcDto;
import com.atticket.product.repository.ShowSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowSeatService {

	// service
	private final HallService hallService;
	private final GradeService gradeService;
	private final SeatService seatService;
	private final ReservedSeatService reservedSeatService;

	// feignClient
	private final ReservationFeignClient reservationFeignClient;

	// repository
	private final ShowSeatRepository showSeatRepository;

	/**
	 * 공연의 남은 좌석 조회
	 * */
	public List<GetRemainSeatsSvcDto> getRemainSeatsByShowId(Long showId) {
		// ShowSeat(공연 등급별 좌석 리스트) 리스트
		List<ShowSeat> showSeats = showSeatRepository.findShowSeatByShowId(showId);
		// 예약된 좌석 id 리스트
		List<Long> reservedSeatIdList = reservationFeignClient.getReservationSeats(showId)
			.getData().reservedSeats.stream()
			.map(reservedSeat -> reservedSeat.getSeatId())
			.collect(Collectors.toList());

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
		List<ReservedSeat> reservedSeats = reservedSeatService.getReservedSeatsByShowId(showId);

		List<GetRemainSeatCntSvcDto> serviceDtoList = new ArrayList<>();

		//등급별 남은 좌석 :showSeats  등급별 좌석  -  예매 좌석
		for (ShowSeat showSeat : showSeats) {
			List<Long> seats = showSeat.getSeats().stream().map(Seat::getId).collect(Collectors.toList());
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
	 * @param show
	 * @param gradeId
	 * @param seats
	 * @return
	 */
	public Long registerShowSeat(Show show, Long gradeId, List<Long> seats) {

		ShowSeat showSeat = ShowSeat.builder()
			.show(show)
			.grade(gradeService.getGradeById(gradeId))
			.seats(seatService.getSeatsByIdList(seats))
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
