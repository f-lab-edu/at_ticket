package com.atticket.product.service;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.Hall;
import com.atticket.product.repository.HallRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HallService {

	// repository
	private final HallRepository hallRepository;

	public Hall getHallById(Long id) {
		return hallRepository.findById(id).orElse(null);
	}
}
