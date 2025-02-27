package attendance.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Year;

import static attendance.domain.AttendanceStatusChecker.*;
import static org.assertj.core.api.Assertions.*;

public class AttendanceStatusCheckerTest {

    @CsvSource({
            "24, 13, 0, ATTENDANCE",
            "24, 13, 5, ATTENDANCE",
            "24, 13, 6, LATE",
            "24, 13, 30, LATE",
            "24, 13, 31, ABSENT",
            "25, 10, 0, ATTENDANCE",
            "25, 10, 5, ATTENDANCE",
            "25, 10, 6, LATE",
            "25, 10, 30, LATE",
            "25, 10, 31, ABSENT",
    })
    @ParameterizedTest
    void 출석_시간을_주면_출석_상태를_알려준다(int day, int hour, int minute, AttendanceStatus expected) {
        // Given
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                Year.of(2025).atMonth(2).atDay(day).atTime(hour, minute));

        // When
        AttendanceStatus attendanceStatus = checkStatus(attendanceDateTime);

        // Then
        assertThat(attendanceStatus).isEqualTo(expected);
    }
}
