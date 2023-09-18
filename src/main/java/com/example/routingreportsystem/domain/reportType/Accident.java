package com.example.routingreportsystem.domain.reportType;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.myEnum.AccidentType;
import com.example.routingreportsystem.myEnum.ReportStatus;
import com.example.routingreportsystem.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@DiscriminatorValue("ACCIDENT")
@NoArgsConstructor
@Getter
@Setter
public class Accident extends Report {
    private AccidentType description;

    public Accident(Point point, User user, AccidentType accidentType) {
        super(point, ReportStatus.UNKNOWN, user, 5);
        this.description = accidentType;
    }

}
