package attendance.dto;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewStatistic;
import attendance.domain.CrewStatistics;
import attendance.domain.Crews;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceExpelDtoTest {
    private static final List<List<String>> ATTENDANCE_RECORDS = List.of(
            List.of("빙봉", "2025-02-20 10:09"),
            List.of("빙티", "2025-02-19 10:08"),
            List.of("쿠키", "2025-02-18 10:06"),
            List.of("빙티", "2025-02-17 13:03"),
            List.of("빙봉", "2025-02-14 10:02"),
            List.of("쿠키", "2025-02-13 10:07"),
            List.of("빙봉", "2025-02-12 10:01"),
            List.of("쿠키", "2025-02-11 10:00"),
            List.of("빙티", "2025-02-10 13:09")
    );

    private final Crews crews = new Crews();
    private final Attendances attendances = new Attendances();
    private CrewStatistics crewStatistics;

    @BeforeEach
    void setUp() {
        crews.initCrews(ATTENDANCE_RECORDS);
        attendances.initAttendances(crews, ATTENDANCE_RECORDS);

        Crew Crew1 = new Crew("쿠키");
        Crew Crew2 = new Crew("빙봉");
        Crew Crew3 = new Crew("빙티");

        CrewStatistic crewStatistic1 = createCrewStatistic(Crew1);
        CrewStatistic crewStatistic2 = createCrewStatistic(Crew2);
        CrewStatistic crewStatistic3 = createCrewStatistic(Crew3);

        crewStatistics = new CrewStatistics(List.of(crewStatistic1, crewStatistic2, crewStatistic3));
    }

    private CrewStatistic createCrewStatistic(Crew crew) {
        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);
        crewStatistic.checkCrewStatistic();
        return crewStatistic;
    }

    @DisplayName("기능: 제적 위험자 출석 정보 반환 목록 초기화 확인")
    @Test
    void getAttendanceExpelInfo() {
        AttendanceExpelDto attendanceExpelDto = AttendanceExpelDto.fromAttendanceExpelRecord(this.crewStatistics);
        assertThat(attendanceExpelDto.attendanceExpelRecords()).hasSize(3);
    }
}
