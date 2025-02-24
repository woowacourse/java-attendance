package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.util.CSVReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @DisplayName("크루와 시간으로 오늘 출석 기록을 추가한다.")
    @Test
    void addAttendanceWithCrewAndDateTime() {
        attendances.attendToday(
                new Crew("빙티"),
                LocalTime.of(9, 58)
        );

        Attendance findAttendance = attendances.findAttendance(
                new Crew("빙티"),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 58))
        );

        assertAll(
                () -> assertThat(findAttendance).extracting("crew").isEqualTo(new Crew("빙티")),
                () -> assertThat(findAttendance).extracting("dateTime")
                        .isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 58))),
                () -> assertThat(findAttendance).extracting("type").isEqualTo(AttendanceType.PRESENT)
        );
    }

    @DisplayName("크루와 날짜로 출석 기록을 조회해서 원하는 시간으로 수정한다.")
    @Test
    void modifyAttendanceWithNewTime() {
        attendances.modifyAttendance(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 14, 10, 1, 0)
        );

        Attendance modifiedAttendance = attendances.findAttendance(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 14, 10, 1, 0)
        );

        assertAll(
                () -> assertThatThrownBy(() -> attendances.findAttendance(
                        new Crew("쿠키"),
                        LocalDateTime.of(2025, 2, 14, 13, 3, 0)
                )).isInstanceOf(IllegalArgumentException.class).hasMessage("크루와 날짜, 시간에 해당하는 출석 기록이 없습니다."),

                () -> assertThat(modifiedAttendance).extracting("dateTime")
                        .isEqualTo(LocalDateTime.of(2025, 2, 14, 10, 1, 0)),
                () -> assertThat(modifiedAttendance).extracting("type").isEqualTo(AttendanceType.PRESENT)
        );
    }
}
