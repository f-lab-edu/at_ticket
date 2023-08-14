package com.atticket.payment.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.atticket.payment.domain.converter.PaymentEntityLinkedPlatformConverter;
import com.atticket.payment.domain.converter.PaymentEntityPgProviderConverter;
import com.atticket.payment.type.LinkedPlatformType;
import com.atticket.payment.type.PgProviderType;

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

	//결제 번호
	private String paymentId;

	//예약 id
	private Long reservationId;

	//주문 id
	private String orderId;

	//판매자 id
	private String sellerId;

	//구매자 id
	private String buyerId;

	//구매자 이름
	private String buyerName;

	//구매자 번호
	private String buyerTel;

	//결제 연동 타입
	@Convert(converter = PaymentEntityLinkedPlatformConverter.class)
	@Column(name = "linked_Platform")
	private LinkedPlatformType linkedPlatform;

	//pg타입
	@Convert(converter = PaymentEntityPgProviderConverter.class)
	@Column(name = "pg_provider")
	private PgProviderType pgProvider;

	//결제 금액
	private Long amount;

	//결제 시간
	private LocalDateTime paidAt;

}
