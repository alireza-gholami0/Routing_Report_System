package com.example.routingreportsystem.service;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.domain.reportType.Accident;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.dto.ReportRequestDto;
import com.example.routingreportsystem.mapper.MapStructReport;
import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.repository.ReportRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository<Report> reportRepository;
    private final MapStructReport mapStructReport;
    private final WKTReader wktReader;
    @Autowired
    public ReportService(ReportRepository<Report> reportRepository, MapStructReport mapStructReport, WKTReader wktReader) {
        this.reportRepository = reportRepository;
        this.mapStructReport = mapStructReport;
        this.wktReader = wktReader;
    }

    private Point pointFromWKT(String txt){
        try {
            return (Point) wktReader.read(txt);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public ReportDto createReport(ReportRequestDto request, User user) {
        Report report = null;
        ReportDto response = null;
        Point point = pointFromWKT(request.point());

        switch (request.type()) {
            case ("ACCIDENT") -> {
                report = new Accident(point, user, AccidentType.getByName(request.description()));
                mapStructReport.ReportToDto((Accident) report);
                break;
            }
            default -> throw new RuntimeException("Invalid report type");
        };
        reportRepository.save(report);
        return response;
    }
}
