package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.ReportStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReportStatusConverter implements AttributeConverter<ReportStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReportStatus reportStatus) {
        return reportStatus.getCode();
    }

    @Override
    public ReportStatus convertToEntityAttribute(Integer integer) {
        return ReportStatus.getByCode(integer);
    }
}
