package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.util.CSVReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancesTest {
    private static final Path path = Paths.get("src/main/resources/attendances.csv");
    private Crews crews;
    private Attendances attendances;

    @BeforeEach
    void setUp() {
        crews = new Crews();
        attendances = new Attendances();

        List<List<String>> csvData = CSVReader.readCSV(path);

        crews.initCrews(csvData);
        attendances.initAttendances(csvData, crews);
        attendances.calculateAttendancesType();
    }

    @DisplayName("크루와 날짜, 시간으로 출석 기록을 조회한다.")
    @Test
    void findAttendanceWithCrewAndDateTime() {
        Crew crew = new Crew("쿠키");
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 14, 13, 3);

        Attendance findAttendance = attendances.findAttendance(crew, dateTime);

        assertAll(
                () -> assertThat(findAttendance).extracting("crew").isEqualTo(crew),
                () -> assertThat(findAttendance).extracting("dateTime").isEqualTo(dateTime),
                () -> assertThat(findAttendance).extracting("type").isEqualTo(AttendanceType.ABSENT)
        );

    }
}
