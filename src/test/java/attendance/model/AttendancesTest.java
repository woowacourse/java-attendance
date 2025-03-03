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
    private List<List<String>> csvData;

    @BeforeEach
    void setUp() {
        csvData = CSVReader.readCSV(path);
    }

    @DisplayName("특정 크루에 해당하는 출석 기록만 가져와서 저장한다.")
    @Test
    void findAttendanceWithCrewAndDateTime() {
        Attendances cookieAttendances = new Attendances();

        cookieAttendances.initAttendances("쿠키", csvData);

        assertAll(
                () -> assertThat(cookieAttendances.getInfo().get(AttendanceType.PRESENT)).isEqualTo(4),
                () -> assertThat(cookieAttendances.getInfo().get(AttendanceType.LATE)).isEqualTo(2)
        );
    }

    @DisplayName("출석 기록을 통계 내서 출석, 지각, 결석 횟수를 반환한다.")
    @Test
    void calculateAttendancesStatus() {
        Attendances attendances = new Attendances();
        attendances.attend(LocalDateTime.of(2025, 2, 25, 9, 58));
        attendances.attend(LocalDateTime.of(2025, 2, 26, 10, 58));
        attendances.attend(LocalDateTime.of(2025, 2, 21, 10, 15));
        attendances.attend(LocalDateTime.of(2025, 2, 20, 9, 58));

        assertAll(
                () -> assertThat(attendances.getInfo().get(AttendanceType.PRESENT)).isEqualTo(2),
                () -> assertThat(attendances.getInfo().get(AttendanceType.LATE)).isEqualTo(1),
                () -> assertThat(attendances.getInfo().get(AttendanceType.ABSENT)).isEqualTo(1)
        );
    }
}