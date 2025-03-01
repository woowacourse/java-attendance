package attendance.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.exception.AttendanceArgumentException;

class AttendancesTest {
    private final SystemDateTime systemDateTime = new AttendanceDateTime();

    @Test
    @DisplayName("출석일이 주말일 경우, 예외가 발생한다")
    void error_cantAttendanceOnWeekend() {
        LocalDateTime weekend = LocalDateTime.of(2024, 12, 14, 10, 0);
        var attendances = new Attendances(systemDateTime);

        Assertions.assertThatThrownBy(() -> attendances.add(weekend))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("출석일이 휴일일 경우, 예외가 발생한다")
    void error_cantAttendanceOnHoliday() {
        LocalDateTime weekend = LocalDateTime.of(2024, 12, 25, 10, 0);
        var attendances = new Attendances(systemDateTime);

        Assertions.assertThatThrownBy(() -> attendances.add(weekend))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 25일 수요일은 등교일이 아닙니다.");
    }
}
