package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.TrafficType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TrafficTypeConverter implements AttributeConverter<TrafficType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TrafficType trafficType) {
        try {
            return trafficType.getCode();
        }
        catch (RuntimeException e){
            throw new RuntimeException("Accident Type { " + trafficType.name() + " } Not Found");
        }
    }

    @Override
    public TrafficType convertToEntityAttribute(Integer integer) {
        return TrafficType.getByCode(integer);
    }
}
