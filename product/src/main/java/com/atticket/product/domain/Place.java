package com.atticket.product.domain;

import com.atticket.product.type.Region;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Place {

	//공연장 id
	private Long id;

	//이름
	private String name;

	//주소
	private String address;

	//전화번호
	private String phoneNumber;

	//지역
	private Region region;
}
