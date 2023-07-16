package com.atticket.payment.domain.converter;

import com.atticket.payment.type.LinkedPlatformType;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class PaymentEntityLinkedPlatformConverter implements AttributeConverter<LinkedPlatformType,String>{
	@Override
	public String convertToDatabaseColumn(LinkedPlatformType attribute) {
		if(Objects.isNull(attribute)){
			throw new NullPointerException("Enum converting String - LinkedPlatformType is null");
		}
		return attribute.toString();
	}

	@Override
	public LinkedPlatformType convertToEntityAttribute(String dbData) {
		return LinkedPlatformType.valueOf(dbData);
	}
}
