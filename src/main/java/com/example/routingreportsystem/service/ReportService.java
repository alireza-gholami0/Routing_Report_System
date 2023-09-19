package com.example.routingreportsystem.service;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.domain.reportType.*;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.dto.ReportRequestDto;
import com.example.routingreportsystem.mapper.MapStructReport;
import com.example.routingreportsystem.myEnum.*;
import com.example.routingreportsystem.repository.ReportRepository;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ShellComponent
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
    private Report reportValidation(long id){
        return reportRepository.findById(id).orElseThrow(()->
                new RuntimeException("Report Not Found"));
    }
    private boolean checkRepeat(List<Report> reports, Long userId, String type) {
        List<Report> a = reports.stream()
                .filter(report -> report.getUser().getId().equals(userId) &&
                        report.getType().equals(type) &&
                        report.getStatus() == ReportStatus.TERMINATED)
                .toList();
        return reports.stream()
                .anyMatch(report -> report.getUser().getId().equals(userId) &&
                        report.getType().equals(type) &&
                        report.getStatus() != ReportStatus.TERMINATED);
    }
    private Point pointFromWKT(String txt){
        try {
            return (Point) wktReader.read(txt);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private LineString lineStringFromWKT(String txt){
        try {
            LineString lineString = (LineString) wktReader.read(txt);
            lineString.setSRID(4326);
            return lineString;
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public ReportDto createReport(ReportRequestDto request, User user) {
        Report report = null;
        ReportDto response = null;
        Point point = pointFromWKT(request.point());
        point.setSRID(4326);
        if(reportRepository.checkRepeat(user, point, request.type()).isEmpty()){
            switch (request.type()) {
                case ("ACCIDENT") -> {
                    report = new Accident(point, user, AccidentType.getByName(request.description()));
                    response = mapStructReport.ReportToDto((Accident) report);
                }
                case ("TRAFFIC") -> {
                    report = new Traffic(point, user, TrafficType.getByName(request.description()));
                    response = mapStructReport.ReportToDto((Traffic) report);
                }
                case ("CAMERA") -> {
                    report = new Camera(point, user, CameraType.getByName(request.description()));
                    response = mapStructReport.ReportToDto((Camera) report);
                }
                case ("POLICE") -> {
                    report = new Police(point, user, PoliceType.getByName(request.description()));
                    response = mapStructReport.ReportToDto((Police) report);
                }
                case ("BUMP") -> {
                    report = new Bump(point, user);
                    response = mapStructReport.ReportToDto((Bump) report);
                }
                default -> throw new RuntimeException("Invalid report type");
            };
        }
        else throw new RuntimeException("The received report is duplicated");

        reportRepository.save(report);
        return response;
    }



    public String like(long id) {
        Report report = reportValidation(id);
        report.setLifeTime(report.getLifeTime()+1);
        reportRepository.save(report);
        return "The Report With Id " + id + " Was Liked";
    }
    public String dislike(long id) {
        Report report = reportValidation(id);
        report.setLifeTime(report.getLifeTime()-1);
        reportRepository.save(report);
        return "The Report With Id " + id + " Was Disliked";
    }

    public List<ReportDto> route(String route) {
        LineString lineString = lineStringFromWKT(route);
        List<Report> reports = reportRepository.findRouteReports(lineString, 15.0);
        List<ReportDto> response = new ArrayList<>();
        reports.forEach(x->{
            System.out.println(x.toString());
            if (x.getReportedAt().plusMinutes(x.getLifeTime()).isBefore(LocalDateTime.now())){
                switch (x.getType()) {
                    case ("ACCIDENT") -> {
                        response.add(mapStructReport.ReportToDto((Accident) x));
                    }
                    case ("TRAFFIC") -> {
                        response.add(mapStructReport.ReportToDto((Traffic) x));
                    }
                    case ("CAMERA") -> {
                        response.add(mapStructReport.ReportToDto((Camera) x));
                    }
                    case ("POLICE") -> {
                        response.add(mapStructReport.ReportToDto((Police) x));
                    }
                    case ("BUMP") -> {
                        response.add(mapStructReport.ReportToDto((Bump) x));
                    }
                    default -> throw new RuntimeException("Invalid report type");
                }
            }
        });
        return response;
    }

    public String adminValidate(long id, boolean status) {
        Report report = reportValidation(id);
        if (status){
            report.setStatus(ReportStatus.ACCEPTED);
            reportRepository.save(report);
        }
        else reportRepository.delete(report);
        return "The Report Was Validated";
    }

    public List<ReportDto> getAll() {
        List<Report> reports = reportRepository.findAll();
        List<ReportDto> response = new ArrayList<>();
        reports.forEach(x->{
            switch (x.getType()) {
                case ("ACCIDENT") -> {
                    response.add(mapStructReport.ReportToDto((Accident) x));
                }
                case ("TRAFFIC") -> {
                    response.add(mapStructReport.ReportToDto((Traffic) x));
                }
                case ("CAMERA") -> {
                    response.add(mapStructReport.ReportToDto((Camera) x));
                }
                case ("POLICE") -> {
                    response.add(mapStructReport.ReportToDto((Police) x));
                }
                case ("BUMP") -> {
                    response.add(mapStructReport.ReportToDto((Bump) x));
                }
                default -> throw new RuntimeException("Invalid report type");
            }
        });
        return response;
    }

    public List<ReportDto> getUnknowns() {
        List<Report> reports = reportRepository.findUnknowns();
        List<ReportDto> response = new ArrayList<>();
        reports.forEach(x->{
            switch (x.getType()) {
                case ("ACCIDENT") -> {
                    response.add(mapStructReport.ReportToDto((Accident) x));
                }
                case ("TRAFFIC") -> {
                    response.add(mapStructReport.ReportToDto((Traffic) x));
                }
                case ("CAMERA") -> {
                    response.add(mapStructReport.ReportToDto((Camera) x));
                }
                case ("POLICE") -> {
                    response.add(mapStructReport.ReportToDto((Police) x));
                }
                case ("BUMP") -> {
                    response.add(mapStructReport.ReportToDto((Bump) x));
                }
                default -> throw new RuntimeException("Invalid report type");
            }
        });
        return response;
    }
    @Scheduled(fixedRate = 60000)
    @ShellMethod(value = "update_status",key = "us")
    public void updateStatus() {
        reportRepository.updateStatus();
    }
}
