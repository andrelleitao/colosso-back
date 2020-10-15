package com.ectoum.colosso.core.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.ectoum.colosso.core.enums.TrueFalse;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TrueFalseConverter implements AttributeConverter<TrueFalse, Integer> {
	@Override
	public Integer convertToDatabaseColumn(TrueFalse trueFalse) {
		if(trueFalse == null) {
			return null;
		}
		
		return trueFalse.getCode();
	}

	@Override
	public TrueFalse convertToEntityAttribute(Integer code) {
		if (code == null) {
            return null;
        }
 
        return Stream.of(TrueFalse.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
	}
}
