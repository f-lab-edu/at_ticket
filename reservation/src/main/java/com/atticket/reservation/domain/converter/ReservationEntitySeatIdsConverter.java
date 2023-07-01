package com.atticket.reservation.domain.converter;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReservationEntitySeatIdsConverter implements AttributeConverter<List<Long>, String> {

	private static final String SEPARATOR = ",";

	@Override
	public String convertToDatabaseColumn(List<Long> attribute) {
		if (Objects.isNull(attribute)) {
			return null;
		}
		return attribute.stream()
			.map(String::valueOf)
			.collect(joining(SEPARATOR));
	}

	@Override
	public List<Long> convertToEntityAttribute(String dbData) {
		if (Objects.isNull(dbData)) {
			return null;
		}
		return Arrays.stream(dbData.split(SEPARATOR))
			.map(Long::parseLong)
			.collect(toList());
	}
}
