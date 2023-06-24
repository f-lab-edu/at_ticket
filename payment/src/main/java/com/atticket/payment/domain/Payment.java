package com.atticket.payment.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.atticket.payment.type.PaymentModule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PAYMENT")
public class Payment {

	//결제id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//예약 id
	private Long reservationId;

	//가격
	private int pirce;

	//결제 시간
	private LocalDateTime time;

	//결제 모듈  id
	private Long moduleId;

	//결제 모듈 타입
	private PaymentModule moduleType;
}
