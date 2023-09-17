package com.example.routingreportsystem.domain.reportType;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.myEnum.CameraType;
import com.example.routingreportsystem.myEnum.ReportStatus;
import com.example.routingreportsystem.myEnum.TrafficType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@DiscriminatorValue("CAMERA")
@NoArgsConstructor
@Getter
@Setter
public class Camera extends Report {
    private CameraType description;

    public Camera(Point point, User user, CameraType cameraType) {
        super(point, ReportStatus.ACCEPTED, user, 7);
        this.description = cameraType;
    }
}