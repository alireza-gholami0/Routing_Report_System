package com.example.routingreportsystem;

import com.example.routingreportsystem.domain.Report;
import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.dto.ReportDto;
import com.example.routingreportsystem.dto.ReportRequestDto;
import com.example.routingreportsystem.mapper.MapStructReport;
import com.example.routingreportsystem.myEnum.ReportType;
import com.example.routingreportsystem.myEnum.Role;
import com.example.routingreportsystem.repository.ReportRepository;
import com.example.routingreportsystem.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.io.WKTReader;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReportServiceTest {
    @Mock
    private ReportRepository<Report> reportRepository;
    @Mock
    private MapStructReport mapStructReport;
    @Mock
    private WKTReader wktReader;
    @Mock
    private RedissonClient redissonClient;
    @InjectMocks
    private ReportService reportService;

    @Test
    public void createReport(){
        User user = User.builder().id(4L).email("alireza@gmail.com").password("123").role(Role.ROLE_USER).build();
        ReportRequestDto request = new ReportRequestDto("ACCIDENT","POINT(-95.93261718749999 44.17589997631143)","LIGHT");
        ReportDto reportDto = reportService.createReport(request, user);
        assertThat(reportDto.type().equals(ReportType.ACCIDENT.toString()));
    }

}
