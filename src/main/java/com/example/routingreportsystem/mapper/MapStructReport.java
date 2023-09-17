package com.example.routingreportsystem.mapper;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.reportType.Accident;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.myEnum.AccidentType;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface MapStructReport {
    @Mapping(target = "point", qualifiedByName = "pointToWkt")
    ReportDto ReportToDto(Accident accident);

    @Named("pointToWkt")
    default String pointToWkt(Point point) {
        return point.toText();
    }
    @ValueMapping(source = "HEAVY", target = "HEAVY")
    @ValueMapping(source = "LIGHT", target = "LIGHT")
    String enumToStr(AccidentType accidentType);
}
