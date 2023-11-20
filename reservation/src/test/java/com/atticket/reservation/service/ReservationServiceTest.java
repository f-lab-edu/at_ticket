package com.atticket.reservation.service;

import com.atticket.reservation.repository.PreReservedSeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationServiceTest {
	@Autowired
	private PreReservedSeatService preReservedSeatService;
	@Autowired
	private PreReservedSeatRepository preReservedSeatRepository;

	@Autowired
	private ReservedSeatService reservedSeatService;

	@Test
	@DisplayName("선예약 1건 테스트")
		// 테스트 완료 후 rollback
	void preRegisterReservationTest() {

		//Given
		Long showId = 1000L;
		List<Long> seatIds = List.of(1L, 2L, 3L);
		String userId = "user1";

		//When
		//선 예약 좌석 등록
		preReservedSeatService.registerPreReservedSeat(showId, seatIds, userId);

		//선예약 테이블에 저장되었나 조회
		Boolean result = preReservedSeatRepository.existsByShowIdAndSeatIdIn(showId, seatIds);
		//저장 결과가 있으면  성공
		assertThat(result).isEqualTo(true);
	}

	@Test
	@DisplayName("10명이 동시에 같은 좌석을 예약하려 했을 경우")
	@Transactional
		// 테스트 완료 후 rollback
	void preRegisterReservationMultiTest() throws Exception {

		//Given
		Long showId = 2L;
		List<Long> seatIds = List.of(1L, 2L, 3L);

		final int people = 100;

		//쓰레드 풀 생성
		ExecutorService executorService = Executors.newFixedThreadPool(people);
		CountDownLatch countDownLatch = new CountDownLatch(people);

		long startTime = System.nanoTime();

		for (int i = 1; i <= people; i++) {

			LocalTime ThreadStartTime = LocalTime.now();
			System.out.println(
				"Thread: " + Thread.currentThread().getName() + ", call idx: " + i + ", startTime: " + ThreadStartTime);

			int finalI = i;

			//쓰레드 실행
			executorService.execute(() -> {
				try {
					System.out.println("예약자:" + finalI);
					//선 예약 좌석 등록
					preReservedSeatService.registerPreReservedSeat(showId, seatIds, String.valueOf(finalI));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}

			});

		}

		countDownLatch.await();

		//선예약 테이블에 저장되었나 조회
		Boolean result = preReservedSeatRepository.existsByShowIdAndSeatIdIn(showId, seatIds);

		//저장 결과가 있으면  성공
		assertThat(result).isEqualTo(true);

		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("nano seconds :" + timeElapsed);
		System.out.println("milli seconds: " + timeElapsed / 1000000);
		System.out.println("seconds : " + (double) timeElapsed / 1_000_000_000);

	}
}
