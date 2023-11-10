package com.atticket.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Seat;
import com.atticket.product.domain.Show;
import com.atticket.product.domain.ShowSeat;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.dto.service.GetShowSeatsSvcDto;
import com.atticket.product.dto.service.RegisterShowServiceDto;
import com.atticket.product.repository.ShowRepository;
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

	//private final KafkaFeignClient kafkaFeignClient;
	private final WishProductService wishProductService;

	// repository
	private final ShowSeatRepository showSeatRepository;
	private final ShowRepository showRepository;

	/**
	 * 공연의 전체 좌석 조회
	 * */
	public List<GetShowSeatsSvcDto> getShowSeats(Long showId) {

		Show show = showService.getShowById(showId);
		// ShowSeat(공연 등급별 좌석 리스트) 리스트
		List<ShowSeat> showSeats = showSeatRepository.findByProduct_idAndHall_id(show.getProduct().getId(),
			show.getHall().getId());
		return showSeats.stream().map(showSeat -> {
			// 등급별로 조회
			// 전체 좌석 id 리스트
			List<Long> seatIdList = convertToSeatIdList(showSeat.getSeats());
			List<Seat> seats = seatService.getSeatsBySeatIds(seatIdList);
			return new GetShowSeatsSvcDto(seats, showSeat.getGrade());
		}).collect(Collectors.toList());
	}

	/**
	 * 등급별 남은 좌석수 조회
	 * @param showId
	 * @return
	 */
	public List<GetRemainSeatCntSvcDto> getRemainSeatCntByShowId(Long showId) {

		Show show = showService.getShowById(showId);
		//공연의 좌석 - 등급 매핑 정보 조회
		List<ShowSeat> showSeats = showSeatRepository.findByProduct_idAndHall_id(show.getProduct().getId(),
			show.getHall().getId());
		//showId로 예매 좌석 리스트 조회
		List<Long> reservedSeatIds = reservedSeatService.getReservedSeatIdsByShowId(showId);

		List<GetRemainSeatCntSvcDto> serviceDtoList = new ArrayList<>();

		//등급별 남은 좌석 :showSeats  등급별 좌석  -  예매 좌석
		showSeats.forEach(showSeat -> {
			List<Long> seats = convertToSeatIdList(showSeat.getSeats());
			int remainSeatCnt = (int)seats.stream().filter(seat -> !reservedSeatIds.contains(seat)).count();
			serviceDtoList.add(
				GetRemainSeatCntSvcDto.builder()
					.showId(showId)
					.gradeId(showSeat.getGrade().getId())
					.gradeNm(showSeat.getGrade().getType())
					.seatCnt(remainSeatCnt)
					.build()
			);
		});

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
		if (!Objects.isNull(showSeatRepository.findByShowId_idAndGrade_id(showId, gradeId))) {
			throw new BaseException(BaseStatus.ALREADY_EXIST_SHOW_SEAT);
		}

		Set<Long> idSet = new HashSet<>(seatIds);
		// 중복된 좌석 id가 있으면 exception return
		if (seatIds.size() != idSet.size()) {
			throw new BaseException(BaseStatus.NO_DUPLICATE_SEAT);
		}

		List<Seat> seats = seatService.getSeatsBySeatIds(seatIds);
		// 유효하지 않은 좌석 id가 있으면 exception return
		if (seatIds.size() != seats.size() || seats.contains(null)) {
			throw new BaseException(BaseStatus.INVALID_SEAT);
		}

		// 홀에 속한 좌석이 아니면 exception
		seats.forEach(seat -> {
			if (!seat.getHall().equals(show.getHall())) {
				throw new BaseException(BaseStatus.HALL_DOES_NOT_INCLUDE_SEAT);
			}
		});

		ShowSeat showSeat = ShowSeat.builder()
			.showId(show)
			.grade(grade)
			.seats(seatConvertToString(seats))
			.build();

		return showSeatRepository.save(showSeat).getId();
	}

	public List<Long> registerShow(Long productId, RegisterShowServiceDto registerShowServiceDto) {
		List<Long> result = registerShowServiceDto.getShowInfos().stream().map(showInfo -> {
			Long showId = showService.saveShow(productId, showInfo.getDate(),
				showInfo.getTime(), showInfo.getHallId(), showInfo.getSession());
			//등록된 공연의 좌석-등급 매핑 저장
			showInfo.getSeatInfos()
				.forEach(seatInfo -> registerShowSeat(showId, seatInfo.getGradeId(), seatInfo.getSeatIds()));
			return showId;
		}).collect(Collectors.toList());

		return result;
	}

	/**
	 * List<Seat>의 좌석 id를 String으로 변환
	 * @param seats
	 * @return
	 */
	private String seatConvertToString(List<Seat> seats) {

		return String.join(",", seats.stream().map(s -> Long.toString(s.getId())).collect(Collectors.toList()));
	}

	/**
	 *좌석id리스트(String)을 List로 변환
	 * @param seatsString
	 * @return
	 */
	private List<Long> convertToSeatIdList(String seatsString) {
		if (StringUtils.hasText(seatsString)) {
			String[] seatStringArray = (seatsString).split(",");
			List<Long> seatsIdList =
				Arrays.stream(seatStringArray).map(x -> Long.parseLong(x)).collect(Collectors.toList());
			return seatsIdList;
		} else {
			throw new BaseException(BaseStatus.UNEXPECTED_ERROR);
		}

	}

	public int deleteByShow(Show show) {
		return showSeatRepository.deleteByShowId(show);
	}

	public Integer getSeatsPrice(Long showId, List<Long> seatIds) {

		System.out.println(showId);
		System.out.println(seatIds);

		List<ShowSeat> showSeats = showSeatRepository.findByShowId_id(showId);

		Integer totalPrice = 0;
		List<Integer> priceList = new ArrayList<>();
		seatIds.forEach(seatId -> {
			System.out.println(seatId);
			ShowSeat showSeatInfo = showSeats.stream().filter(showSeat -> {
				List<Long> showSeatIds = convertToSeatIdList(showSeat.getSeats());
				return showSeatIds.contains(seatId);
			}).findAny().get();
			int price = showSeatInfo.getGrade().getPrice();
			priceList.add(price);
		});

		for (Integer a : priceList) {
			totalPrice += a;
		}

		return totalPrice;
	}
}
