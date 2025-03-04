package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeTest {

    @DisplayName("화요일 10:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_tuesday_10_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        assertAttendanceType(AttendanceTime.from(attendanceDateTime), ATTENDANCE);
    }

    @DisplayName("월요일 13:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_monday_13_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        assertAttendanceType(AttendanceTime.from(attendanceDateTime), ATTENDANCE);
    }

    @DisplayName("월요일 13:05에 출석했을 경우, 지각을 반환해야 한다")
    @Test
    void given_monday_13_05_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        assertAttendanceType(AttendanceTime.from(attendanceDateTime), LATE);
    }

    @DisplayName("월요일 13:30에 출석했을 경우, 결석을 반환해야 한다")
    @Test
    void given_monday_13_30_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);
        assertAttendanceType(AttendanceTime.from(attendanceDateTime), ABSENCE);
    }

    private void assertAttendanceType(AttendanceTime attendanceTime, AttendanceType expectedType) {
        AttendanceType attendanceType = AttendanceType.decideAttendanceType(attendanceTime);
        assertThat(attendanceType).isEqualTo(expectedType);
    }
}
