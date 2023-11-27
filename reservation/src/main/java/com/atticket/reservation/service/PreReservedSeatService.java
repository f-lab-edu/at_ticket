package com.atticket.reservation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.reservation.domain.PreReservedSeat;
import com.atticket.reservation.repository.PreReservedSeatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PreReservedSeatService {

	private final ReservedSeatService reservedSeatService;
	private final PreReservedSeatRepository preReservedSeatRepository;
	private final RedissonClient redissonClient;

	/**
	 * 선예약좌석 등록하기
	 */
	public void registerPreReservedSeat(Long showId, List<Long> seatIds, String userId) {

		//특정 이름으로 락 정의
		final String lockKey = showId + seatIds.toString();
		final RLock lock = redissonClient.getLock(lockKey);

		try {
			// 락 획득을 시도한다(2초동안 시도를 할 예정이며 획득할 경우 1초안에 해제할 예정)
			boolean available = lock.tryLock(2, 1, TimeUnit.SECONDS);

			if (!available) {
				log.info("lock 획득 실패");
				return;
			}

			//예약 테이블 확인
			if (reservedSeatService.existsReservedSeat(showId, seatIds)) {
				throw new BaseException(BaseStatus.EXIST_RESERVED_SEAT);
			}

			//선좌석 테이블에서 여부 확인
			if (preReservedSeatRepository.existsByShowIdAndSeatIdIn(showId, seatIds)) {
				throw new BaseException(BaseStatus.EXIST_RESERVED_SEAT);
			}

			savePreReservedSeat(showId, seatIds, userId);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (lock.isLocked() && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 * 선예약좌석 삭제하기
	 */
	public void deletePreReservedSeat(Long showId, List<Long> seatIds) {
		List<PreReservedSeat> preReservedSeats = preReservedSeatRepository.findByShowIdAndSeatIdIn(showId, seatIds);
		preReservedSeatRepository.deleteAll(preReservedSeats);
	}

	/**
	 * 선예약좌석 정보 저장
	 */
	public void savePreReservedSeat(Long showId, List<Long> seatIds, String userId) {

		List<PreReservedSeat> preReservedSeats = seatIds.stream().map(seatId ->
			PreReservedSeat.builder()
				.showId(showId)
				.seatId(seatId)
				.userId(userId)
				.time(LocalDateTime.now())
				.build()
		).collect(Collectors.toList());
		preReservedSeatRepository.saveAll(preReservedSeats);
	}
}
