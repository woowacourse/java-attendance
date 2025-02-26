package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import attendance.util.CSVReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    private static final Path path = Paths.get("src/main/resources/attendances.csv");


    @DisplayName("이름이 같은 서로 다른 크루객체는 동일 크루로 취급한다.")
    @Test
    void compareCrew() {
        Crew crew1 = new Crew("엠제이");
        Crew crew2 = new Crew("엠제이");

        assertEquals(crew1, crew2);
    }

    @DisplayName("크루가 출석을 한다.")
    @Test
    void crewAttend() {
        Crew crew = new Crew("이든");
        crew.attendToday(LocalTime.of(9, 58));

        assertAll(
                () -> assertThat(crew.getPresentCount()).isEqualTo(1),
                () -> assertThat(crew.getLateCount()).isEqualTo(0),
                () -> assertThat(crew.getAbsentCount()).isEqualTo(0)
        );
    }

    @DisplayName("날짜로 크루의 출석 기록을 조회한다.")
    @Test
    void findAttendanceInfoByDate() {
        List<List<String>> csvData = CSVReader.readCSV(path);
        Crew cookie = new Crew("쿠키");
        cookie.initCrewAttendances(csvData);

        assertThat(cookie.findAttendance(LocalDate.of(2025, 2, 13)).getType()).isEqualTo(AttendanceType.LATE);
    }

    @DisplayName("날짜로 크루의 출석 기록을 찾아서 시간을 수정한다.")
    @Test
    void modifyAttendance() {
        List<List<String>> csvData = CSVReader.readCSV(path);
        Crew bingbong = new Crew("빙봉");
        bingbong.initCrewAttendances(csvData);

        assertAll(
                () -> assertThat(bingbong.findAttendance(LocalDate.of(2025, 2, 14)).getType()).isEqualTo(
                        AttendanceType.ABSENT),
                () -> assertThatCode(
                        () -> bingbong.modifyAttendance(
                                LocalDateTime.of(2025, 2, 14, 10, 0, 0))).doesNotThrowAnyException(),
                () -> assertThat(bingbong.findAttendance(LocalDate.of(2025, 2, 14)).getType()).isEqualTo(
                        AttendanceType.PRESENT)
        );
    }
}
