package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.CameraType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CameraTypeConverter implements AttributeConverter<CameraType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CameraType cameraType) {
        try {
            return cameraType.getCode();
        }
        catch (RuntimeException e){
            throw new RuntimeException("Camera Type { " + cameraType.name() + " } Not Found");
        }
    }

    @Override
    public CameraType convertToEntityAttribute(Integer integer) {
        return CameraType.getByCode(integer);
    }
}
