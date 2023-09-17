package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.PoliceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PoliceTypeConverter implements AttributeConverter<PoliceType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PoliceType policeType) {
        try {
            return policeType.getCode();
        }
        catch (RuntimeException e){
            throw new RuntimeException("Police Type { " + policeType.name() + " } Not Found");
        }
    }

    @Override
    public PoliceType convertToEntityAttribute(Integer integer) {
        return PoliceType.getByCode(integer);
    }
}
