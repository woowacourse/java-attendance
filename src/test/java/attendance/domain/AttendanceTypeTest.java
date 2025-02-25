package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeTest {

    @DisplayName("value값이 4가 들어왔을 경우, 출석이 반환되어야 한다.")
    @Test
    void value_is_5_then_return_attendance() {
        int value = 4;
        AttendanceType attendanceType = AttendanceType.determineAttendanceTypeByLateTime(value);
        assertThat(attendanceType).isEqualTo(ATTENDANCE);
    }

    @DisplayName("value값이 5가 들어왔을 경우, 지각이 반환되어야 한다.")
    @Test
    void value_is_6_then_return_late() {
        int value = 5;
        AttendanceType attendanceType = AttendanceType.determineAttendanceTypeByLateTime(value);
        assertThat(attendanceType).isEqualTo(LATE);
    }

    @DisplayName("value값이 29가 들어왔을 경우, 지각이 반환되어야 한다.")
    @Test
    void value_is_29_then_return_late() {
        int value = 29;
        AttendanceType attendanceType = AttendanceType.determineAttendanceTypeByLateTime(value);
        assertThat(attendanceType).isEqualTo(LATE);
    }

    @DisplayName("value값이 30이 들어왔을 경우, 결석이 반환되어야 한다.")
    @Test
    void value_is_30_then_return_absence() {
        int value = 30;
        AttendanceType attendanceType = AttendanceType.determineAttendanceTypeByLateTime(value);
        assertThat(attendanceType).isEqualTo(ABSENCE);
    }
}
