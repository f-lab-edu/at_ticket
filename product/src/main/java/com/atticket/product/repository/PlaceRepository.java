package com.atticket.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
	
}
