package com.atticket.common.kafka.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WishProduct {

	private Long productId;

	private String productNm;

	private List<String> showData;

	private List<UserData> userData;

	@Builder
	@Getter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserData {

		private String id;
		private String email;

	}

}
