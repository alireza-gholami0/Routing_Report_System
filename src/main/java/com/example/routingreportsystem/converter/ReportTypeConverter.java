package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.ReportType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReportTypeConverter implements AttributeConverter<ReportType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReportType reportType) {
        try {
            return reportType.getCode();
        }
        catch (RuntimeException e){
            throw new RuntimeException("Report Type { " + reportType.name() + " } Not Found");
        }
    }

    @Override
    public ReportType convertToEntityAttribute(Integer integer) {
        return ReportType.getByCode(integer);
    }
}
