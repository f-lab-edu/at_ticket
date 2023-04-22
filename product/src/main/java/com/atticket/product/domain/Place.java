package com.atticket.product.domain;

import com.atticket.product.type.Region;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {

	private String id;
	private String name;
	private String address;
	private String phoneNumber;
	private Region region;
}
