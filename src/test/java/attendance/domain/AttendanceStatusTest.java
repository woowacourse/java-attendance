package attendance.domain;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 상태 Enum")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource({
            "10, 5, PRESENT",
            "9, 5, PRESENT",
            "10, 6, LATENESS",
            "10, 30, LATENESS",
            "10, 31, ABSENCE",
            "12, 50, ABSENCE"
    })
    void 평일_출석_시간으로_출석_상태를_반환한다(int hour, int minute, AttendanceStatus expected) {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 24, hour, minute);
        AttendanceStatus actual = AttendanceStatus.checkAttendance(attendanceTime);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "13, 5, PRESENT",
            "10, 5, PRESENT",
            "13, 6, LATENESS",
            "13, 30, LATENESS",
            "13, 31, ABSENCE",
            "15, 50, ABSENCE"
    })
    void 월요일_출석_시간으로_출석_상태를_반환한다(int hour, int minute, AttendanceStatus expected) {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 23, hour, minute);
        AttendanceStatus actual = AttendanceStatus.checkAttendance(attendanceTime);

        assertThat(actual).isEqualTo(expected);
    }

}
