package com.example.routingreportsystem.mapper;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.reportType.Accident;
import com.example.routingreportsystem.domain.reportType.Traffic;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.TrafficType;
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
    @Mapping(target = "point", qualifiedByName = "pointToWkt")
    ReportDto ReportToDto(Traffic traffic);

    @Named("pointToWkt")
    default String pointToWkt(Point point) {
        return point.toText();
    }
    @ValueMapping(source = "HEAVY", target = "HEAVY")
    @ValueMapping(source = "LIGHT", target = "LIGHT")
    String enumToStr(AccidentType accidentType);

    @ValueMapping(source = "HEAVY", target = "HEAVY")
    @ValueMapping(source = "SEMI_HEAVY", target = "SEMI_HEAVY")
    @ValueMapping(source = "LIGHT", target = "LIGHT")
    String enumToStr(TrafficType trafficType);
}
