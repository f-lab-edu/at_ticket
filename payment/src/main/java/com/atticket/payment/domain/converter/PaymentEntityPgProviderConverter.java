package com.atticket.payment.domain.converter;

import com.atticket.payment.type.PgProviderType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class PaymentEntityPgProviderConverter implements AttributeConverter<PgProviderType,String> {

	public String convertToDatabaseColumn(PgProviderType attribute) {
		if(Objects.isNull(attribute)){
			throw new NullPointerException("Enum converting String - PgProviderType is null");
		}
		return attribute.toString();
	}

	@Override
	public PgProviderType convertToEntityAttribute(String dbData) {
		return PgProviderType.valueOf(dbData);
	}
}
