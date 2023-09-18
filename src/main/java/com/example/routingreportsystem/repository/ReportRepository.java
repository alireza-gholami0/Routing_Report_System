package com.example.routingreportsystem.repository;

import com.example.routingreportsystem.domain.Report;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository<T extends Report> extends JpaRepository<Report, Long> {
    @Query(value = "SELECT r FROM Report r WHERE ST_DWithin(st_transform(r.point, 3857), st_transform(:lineString, 3857), :distance) = true AND r.status=1")
    List<Report> findRouteReports(@Param("lineString") LineString lineString, @Param("distance") double distance);
}
