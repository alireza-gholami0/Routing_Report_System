package com.example.routingreportsystem.domain;

import com.example.routingreportsystem.myEnum.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "point")
    private Point point;
    @Column(name = "reported_at")
    private LocalDateTime reportedAt;
    @Column(name = "life_time")
    private int lifeTime;
    @Column(name = "status")
    private ReportStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "type")
    private String type;
    public Report(Point point, ReportStatus status, User user, int lifeTime) {
        this.point = point;
        point.setSRID(4326);
        this.reportedAt = LocalDateTime.now();
        this.status = status;
        this.user = user;
        this.lifeTime = lifeTime;
        setType();
    }
    private void setType() {
        this.type = this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
