package com.atticket.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "WishProduct")
public class WishProduct {
	//관심상품을 등록한 유저 정보

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//물품 id
	private Long productId;

	//물품 이름
	private String name;

	//유저 id
	private String userId;

	//유저 email
	private String userEmail;

}
