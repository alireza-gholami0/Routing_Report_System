package com.example.routingreportsystem.domain.reportType;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.myEnum.ReportStatus;
import com.example.routingreportsystem.myEnum.TrafficType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@DiscriminatorValue("TRAFFIC")
@NoArgsConstructor
@Getter
@Setter
public class Traffic extends Report {
    private TrafficType description;

    public Traffic(Point point, User user, TrafficType trafficType) {
        super(point, ReportStatus.ACCEPTED, user, 7);
        this.description = trafficType;
    }
}