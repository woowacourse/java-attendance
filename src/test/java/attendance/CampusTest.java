package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusTest {
    @DisplayName("정규 시작 시간 출석 테스트")
    @ParameterizedTest
    @CsvSource({"3,8,0,0", "3,9,59,59", "3,10,0,0", "3,10,5,0", "24,8,0,0", "24,9,59,59", "24,10,0,0", "24,10,5,0"})
    void test1(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.PRESENT;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정규 시작 시간 지각 테스트")
    @ParameterizedTest
    @CsvSource({"3,10,5,1", "3,10,30,0", "3,10,17,59", "24,10,5,1", "24,10,30,0", "24,10,29,0"})
    void test2(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.LATE;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정규 시작 시간 결석 테스트")
    @ParameterizedTest
    @CsvSource({"3,10,30,1", "3,10,31,0", "3,18,0,0", "3,23,0,0", "24,10,30,1", "24,10,31,0", "24,18,0,0", "24,23,0,0"})
    void test3(int dayOfMonth, int hour, int minute) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute);
        AttendanceStatus expected = AttendanceStatus.ABSENT;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }
}
