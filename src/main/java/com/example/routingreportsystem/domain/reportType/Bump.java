package com.example.routingreportsystem.domain.reportType;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.ReportStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@DiscriminatorValue("BUMP")
@NoArgsConstructor
@Getter
@Setter
public class Bump extends Report {

    public Bump(Point point, User user) {
        super(point, ReportStatus.ACCEPTED, user, 50);
    }
}
