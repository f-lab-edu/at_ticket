package com.atticket.commonenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgeLimit {

	All(1, "전체 이용가"),
	EIGHT(8, "8세 이상 이용가"),
	TWELVE(12, "12세 이상 이용가"),
	FIFTEEN(15, "15세 이상 이용가"),
	ADULT(18, "청소년 이용불가");

	private final int age;
	private final String explain;
}
