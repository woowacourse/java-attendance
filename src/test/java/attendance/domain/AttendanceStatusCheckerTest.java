package attendance.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Year;

import static org.assertj.core.api.Assertions.*;

public class AttendanceStatusCheckerTest {

    @CsvSource({
            "10, 0, AttendanceStatus.ATTENDANCE",
            "10, 5, AttendanceStatus.ATTENDANCE",
            "10, 6, AttendanceStatus.LATE",
            "10, 30, AttendanceStatus.LATE",
            "10, 31, AttendanceStatus.ABSENT",
    })
    @ParameterizedTest
    void 출석_시간을_주면_출석_상태를_알려준다(int hour, int minute, AttendanceStatus expected) {
        // Given
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                Year.of(2025).atMonth(2).atDay(27).atTime(hour, minute));

        // When
        AttendanceStatus attendanceStatus = AttendanceStatusChecker.checkStatus(attendanceDateTime);

        // Then
        assertThat(attendanceStatus).isEqualTo(expected);
    }
}
