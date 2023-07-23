package com.atticket.product.client.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostNotifyProductToMailDto {

	private String title;
	private String message;

}
