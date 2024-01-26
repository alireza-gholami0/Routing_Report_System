package com.example.routingreportsystem.repository;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository<T extends Report> extends JpaRepository<Report, Long> {

    @Query("""
        SELECT r
        FROM Report r
        WHERE r.user = :user AND r.type = :type AND r.point = :point AND r.status != 3
        """)
    List<Report> checkRepeat(@Param("user") User user, @Param("point") Point point, @Param("type") String type);

    @Query(value = """
            SELECT r.* FROM report r
        WHERE ST_DWithin(st_transform(r.point, 3857), st_transform(:lineString, 3857), :distance) = true AND r.status=1
        AND (r.reported_at + (INTERVAL '1 minute' * r.life_time)) <= NOW()
    """, nativeQuery = true)
    List<Report> findRouteReports(@Param("lineString") LineString lineString, @Param("distance") double distance);
    @Query("""
        SELECT r FROM Report r
        WHERE r.status = 0
        """)
    List<Report> findUnknowns();
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE report
        SET status = 3
        WHERE (reported_at + (INTERVAL '1 minute' * life_time)) <= NOW()
        """, nativeQuery = true)
    void updateStatus();
}
