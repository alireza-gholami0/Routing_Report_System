package com.example.routingreportsystem.repository;

import com.example.routingreportsystem.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository<T extends Report> extends JpaRepository<Report, Long> {
//    @Query("update Report as r SET r.status = 'TERMINATED' WHERE r.reportedAt")
}
