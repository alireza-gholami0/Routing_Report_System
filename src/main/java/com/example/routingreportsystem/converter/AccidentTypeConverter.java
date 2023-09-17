package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.AccidentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccidentTypeConverter implements AttributeConverter<AccidentType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccidentType accidentType) {
        try {
            return accidentType.getCode();
        }
        catch (RuntimeException e){
            throw new RuntimeException("Accident Type { " + accidentType.name() + " } Not Found");
        }
    }

    @Override
    public AccidentType convertToEntityAttribute(Integer integer) {
        return AccidentType.getByCode(integer);
    }
}
