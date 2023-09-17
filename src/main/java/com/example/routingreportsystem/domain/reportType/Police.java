package com.example.routingreportsystem.domain.reportType;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.PoliceType;
import com.example.routingreportsystem.myEnum.ReportStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@DiscriminatorValue("POLICE")
@NoArgsConstructor
@Getter
@Setter
public class Police extends Report {
    private PoliceType description;

    public Police(Point point, User user, PoliceType policeType) {
        super(point, ReportStatus.UNKNOWN, user, 60);
        this.description = policeType;
    }
}
