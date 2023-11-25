package com.atticket.common.kafka.topic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Topic {

	NOTIFY_MAIL("메일 알림"),
	CHECK_RESERVATION("예약 여부 저장");

	private final String explain;

}
