package com.ectoum.colosso.core.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.ectoum.colosso.core.enums.YesNo;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class YesNoConverter implements AttributeConverter<YesNo, String> {
 
    @Override
    public String convertToDatabaseColumn(YesNo yesNo) {
        if (yesNo == null) {
            return null;
        }
        
        return yesNo.getCode();
    }
 
    @Override
    public YesNo convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
 
        return Stream.of(YesNo.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
