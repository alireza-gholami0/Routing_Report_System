package com.example.routingreportsystem.controller;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.dto.ReportRequestDto;
import com.example.routingreportsystem.service.ReportService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService service;
    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }
    @GetMapping("get-all")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<ReportDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }
    @GetMapping("get-unknowns")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<ReportDto>> getUnknowns(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUnknowns());
    }
    @PutMapping("/check/{id}/{status}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> checkReport(@PathVariable long id, @PathVariable boolean status){
        return ResponseEntity.status(HttpStatus.OK).body(service.check(id, status));
    }

    @PostMapping("/add")
    public ResponseEntity<ReportDto> addReport(@RequestBody ReportRequestDto request, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(service.createReport(request, user));
    }
    @PutMapping("/like/{id}")
    public ResponseEntity<String> likeReport(@PathVariable(name = "id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.like(id));
    }
    @PutMapping("/dislike/{id}")
    public ResponseEntity<String> dislikeReport(@PathVariable(name = "id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.dislike(id));
    }
    @GetMapping("/route")
    public ResponseEntity<List<ReportDto>> routeReports(@RequestParam(name = "route") String route){
        return ResponseEntity.status(HttpStatus.OK).body(service.route(route));
    }
}
