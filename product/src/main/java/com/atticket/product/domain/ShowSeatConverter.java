package com.atticket.product.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ShowSeatConverter implements AttributeConverter<List<Seat>, String> {

	@Override
	public String convertToDatabaseColumn(List<Seat> seats) {

		if (Objects.isNull(seats)) {
			return null;
		}

		return String.join(",", seats.stream().map(s -> Long.toString(s.getId())).collect(Collectors.toList()));
	}

	@Override
	public List<Seat> convertToEntityAttribute(String dbData) {
		//Todo: 수정필요
		
		// if (Objects.isNull(dbData)) {
		// 	return null;
		// }
		//
		// List<Long> seatList = new ArrayList<>();
		// if (StringUtils.hasText(dbData)) {
		// 	String[] seatString = (dbData).split(",");
		// 	Arrays.stream(seatString).forEach(x -> seatList.add(Long.parseLong(x)));
		// }
		//
		// return seatList.stream().map(x -> Seat.findById(x)).collect(Collectors.toList());

		List<Seat> seats =
			List.of(
				Seat.builder()
					.id(1L)
					.locX("1")
					.locY("2")
					.build()
			);
		return seats;
	}
}

