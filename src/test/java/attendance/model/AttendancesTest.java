package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.util.CSVReader;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                () -> assertThat(cookieAttendances.calculatePresentCount()).isEqualTo(4),
                () -> assertThat(cookieAttendances.calculateLateCount()).isEqualTo(2)
        );
    }
}