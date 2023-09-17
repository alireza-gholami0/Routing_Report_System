package com.example.routingreportsystem.controller;

import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.dto.ReportRequestDto;
import com.example.routingreportsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService service;
    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<ReportDto> addReport(@RequestBody ReportRequestDto request, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(service.createReport(request, user));
    }
}
