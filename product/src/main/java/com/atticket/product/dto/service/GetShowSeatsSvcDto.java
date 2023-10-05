package com.atticket.product.dto.service;

import java.util.List;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Seat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetShowSeatsSvcDto {

	private List<Seat> seats;

	private Grade grade;
}
