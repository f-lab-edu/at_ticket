package com.atticket.payment.client.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetIamPortAccessTokenReqDto {
	private final String imp_key;
	private final String imp_secret;
}
