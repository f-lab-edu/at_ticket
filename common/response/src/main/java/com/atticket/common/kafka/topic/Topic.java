package com.atticket.common.kafka.topic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Topic {

	NOTIFY_MAIL("메일 알림");

	private final String explain;

}
