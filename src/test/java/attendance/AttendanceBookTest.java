package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendanceBook.Attendance;
import attendance.domain.attendanceBook.AttendanceBook;

public class AttendanceBookTest {
    private AttendanceBook manager;
    private static final String TEST_FILE = "/attendances.csv";

    @BeforeEach
    void setUp() throws AttendanceFileException {
        var repository = new AttendanceFileReader(TEST_FILE);
        var lines = repository.getLines();
        manager = AttendanceBook.from(lines);
    }

    @ParameterizedTest
    @MethodSource("getSourceForAttendanceInfo")
    @DisplayName("csv 파일로부터 출석 정보를 불러온다.")
    void test_getAttendanceInfoFromCSV(String nickname, Attendance attendance) {
        assertThat(manager.findAttendance(nickname, attendance)).isEqualTo(attendance);
    }

    private static Stream<Arguments> getSourceForAttendanceInfo() {
        return Stream.of(
            Arguments.arguments(
                "이든",
                new Attendance(LocalDateTime.of(2024, 12, 3, 10, 6))
            ),
            Arguments.arguments(
                "빙티",
                new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6))
            ),
            Arguments.arguments(
                "짱수",
                new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0))
            )
        );
    }
}
