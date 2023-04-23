package com.atticket.product.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Hall {
	private Long id;
	private String name;
	private Long hallId;

}
