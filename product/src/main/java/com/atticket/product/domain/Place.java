package com.atticket.product.domain;

import com.atticket.product.type.Region;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Place {

	private Long id;
	private String name;
	private String address;
	private String phoneNumber;
	private Region region;
}
