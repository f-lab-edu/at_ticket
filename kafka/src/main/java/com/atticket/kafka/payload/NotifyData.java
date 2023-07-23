package com.atticket.kafka.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotifyData {

	private String title;
	private String message;

}
