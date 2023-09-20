package com.example.routingreportsystem.service;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.domain.reportType.*;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.dto.ReportRequestDto;
import com.example.routingreportsystem.mapper.MapStructReport;
import com.example.routingreportsystem.myEnum.*;
import com.example.routingreportsystem.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ShellComponent
public class ReportService {
    private final ReportRepository<Report> reportRepository;
    private final MapStructReport mapStructReport;
    private final WKTReader wktReader;
    private final RedissonClient redissonClient;

    @Autowired
    public ReportService(ReportRepository<Report> reportRepository, MapStructReport mapStructReport, WKTReader wktReader, RedissonClient redissonClient) {
        this.reportRepository = reportRepository;
        this.mapStructReport = mapStructReport;
        this.wktReader = wktReader;
        this.redissonClient = redissonClient;
    }

    private Report reportValidation(long id){
        return reportRepository.findById(id).orElseThrow(()->
                new RuntimeException("Report Not Found"));
    }
    private Point pointFromWKT(String txt){
        try {
            Point point = (Point) wktReader.read(txt);
            point.setSRID(4326);
            return point;
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
    public List<ReportDto> dispatch(List<Report> reports){
        List<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(x->{
            switch (x.getType()) {
                case ("ACCIDENT") -> {
                    reportDtos.add(mapStructReport.reportToDto((Accident) x));
                }
                case ("TRAFFIC") -> {
                    reportDtos.add(mapStructReport.reportToDto((Traffic) x));
                }
                case ("CAMERA") -> {
                    reportDtos.add(mapStructReport.reportToDto((Camera) x));
                }
                case ("POLICE") -> {
                    reportDtos.add(mapStructReport.reportToDto((Police) x));
                }
                case ("BUMP") -> {
                    reportDtos.add(mapStructReport.reportToDto((Bump) x));
                }
                default -> throw new RuntimeException("Invalid report type");
            }
        });
        return reportDtos;
    }
    @Transactional
    public ReportDto createReport(ReportRequestDto request, User user) {
        Report report = null;
        ReportDto response = null;
        Point point = pointFromWKT(request.point());
        if(reportRepository.checkRepeat(user, point, request.type()).isEmpty()){
            switch (request.type()) {
                case ("ACCIDENT") -> {
                    report = new Accident(point, user, AccidentType.getByName(request.description()));
                    response = mapStructReport.reportToDto((Accident) report);
                }
                case ("TRAFFIC") -> {
                    report = new Traffic(point, user, TrafficType.getByName(request.description()));
                    response = mapStructReport.reportToDto((Traffic) report);
                }
                case ("CAMERA") -> {
                    report = new Camera(point, user, CameraType.getByName(request.description()));
                    response = mapStructReport.reportToDto((Camera) report);
                }
                case ("POLICE") -> {
                    report = new Police(point, user, PoliceType.getByName(request.description()));
                    response = mapStructReport.reportToDto((Police) report);
                }
                case ("BUMP") -> {
                    report = new Bump(point, user);
                    response = mapStructReport.reportToDto((Bump) report);
                }
                default -> throw new RuntimeException("Invalid report type");
            };
        }
        else throw new RuntimeException("The received report is duplicated");
        RLock lock = redissonClient.getFairLock(
                "lock"+ report.getPoint().toString());
        lock.lock();
        reportRepository.save(report);
        lock.unlock();
        return response;
    }
    @Transactional
    public String like(long id) {
        Report report = reportValidation(id);
        report.setLifeTime(report.getLifeTime()+1);
        RLock lock = redissonClient.getFairLock(
                "lock"+ report.getPoint().toString());
        lock.lock();
        reportRepository.save(report);
        lock.unlock();
        return "The Report With Id " + id + " Was Liked";
    }
    @Transactional
    public String dislike(long id) {
        Report report = reportValidation(id);
        report.setLifeTime(report.getLifeTime()-1);
        RLock lock = redissonClient.getFairLock(
                "lock"+ report.getPoint().toString());
        lock.lock();
        reportRepository.save(report);
        lock.unlock();
        return "The Report With Id " + id + " Was Disliked";
    }

    public List<ReportDto> route(String route) {
        LineString lineString = lineStringFromWKT(route);
        List<Report> reports = reportRepository.findRouteReports(lineString, 15.0);
        return dispatch(reports);
    }
    @Transactional
    public String adminValidate(long id, boolean status) {
        Report report = reportValidation(id);
        RLock lock = redissonClient.getFairLock(
                "lock"+ report.getPoint().toString());
        lock.lock();
        if (status){
            report.setStatus(ReportStatus.ACCEPTED);
            reportRepository.save(report);
        }
        else reportRepository.delete(report);
        lock.unlock();
        return "The Report Was Validated";
    }

    public List<ReportDto> getAll() {
        List<Report> reports = reportRepository.findAll();
        return dispatch(reports);
    }

    public List<ReportDto> getUnknowns() {
        List<Report> reports = reportRepository.findUnknowns();
        return dispatch(reports);
    }
    @Transactional
    @Scheduled(fixedRate = 60000)
    @ShellMethod(value = "update_status",key = "us")
    public void updateStatus() {
        reportRepository.updateStatus();
    }
}
