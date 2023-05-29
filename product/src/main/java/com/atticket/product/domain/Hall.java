package com.atticket.product.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity
@Table(name = "HALL")
public class Hall {

	//공연홀 Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//이름
	private String name;

	//공연장 Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;
}
