package com.atticket.product.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterShowResDto {

	private final List<Long> showIds;

}
