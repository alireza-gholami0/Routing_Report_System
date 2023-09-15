package com.example.routingreportsystem.domain.reportType;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.ReportStatus;
import com.example.routingreportsystem.myEnum.ReportType;
import com.example.routingreportsystem.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@DiscriminatorValue("accident")
@NoArgsConstructor
@Getter
@Setter
public class Accident extends Report {
    private AccidentType accidentType;

    public Accident(Point point, ReportType type, ReportStatus status, User user, AccidentType accidentType) {
        super(point, type, status, user);
        this.accidentType = accidentType;
    }
}
