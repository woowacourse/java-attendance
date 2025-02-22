package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.util.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewStatisticTest {
    private Attendances attendances;

    @BeforeEach
    void setUp() {
        attendances = new Attendances();
        Crews crews = new Crews();

        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource("attendances.csv");

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    @DisplayName("해당 크루의 원하는 출석 정보를 확인한다.")
    @Test
    void getCrewStatistic() {
        Crew cookie = new Crew("쿠키");
        List<Attendance> cookieAttendances = attendances.findCrewAttendances(cookie);

        CrewStatistic cookieStatistic = new CrewStatistic(cookie, cookieAttendances);
        cookieStatistic.initCrewsStatus();
        cookieStatistic.calculatePenalty();

        assertAll(
                () -> assertThat(cookieStatistic)
                        .extracting("crew")
                        .extracting("safeCount")
                        .isEqualTo(4),
                () -> assertThat(cookieStatistic)
                        .extracting("status")
                        .isEqualTo(CrewStatus.EXPEL)
        );

    }
}