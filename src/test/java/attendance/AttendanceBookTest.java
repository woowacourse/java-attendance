package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import attendance.domain.AttendanceFileReader;
import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.exception.AttendanceFileException;

public class AttendanceBookTest {
    private final AttendanceFileReader attendanceFileReader = AttendanceFileReader.from("/attendances.csv");
    private final AttendanceBook attendanceBook = AttendanceBook.from(attendanceFileReader.getLines());

    public AttendanceBookTest() throws AttendanceFileException {
    }

    @ParameterizedTest
    @MethodSource("getSourceForAttendanceInfo")
    @DisplayName("csv 파일로부터 출석 정보를 불러온다.")
    void test_getAttendanceInfoFromCSV(String nickname, Attendance attendance) {
        assertThat(attendanceBook.findAttendance(nickname, attendance)).isEqualTo(attendance);
    }

    private static Stream<Arguments> getSourceForAttendanceInfo() {
        return Stream.of(
            Arguments.arguments(
                "이든",
                Attendance.from(LocalDateTime.of(2024, 12, 3, 10, 6))
            ),
            Arguments.arguments(
                "빙티",
                Attendance.from(LocalDateTime.of(2024, 12, 5, 10, 6))
            ),
            Arguments.arguments(
                "짱수",
                Attendance.from(LocalDateTime.of(2024, 12, 3, 10, 0))
            )
        );
    }
}
