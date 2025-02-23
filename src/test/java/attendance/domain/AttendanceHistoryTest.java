package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @DisplayName("AttendanceHistory 객체 생성 테스트")
    @Test
    void create_attendance_history() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 1);
        AttendanceHistory attendanceHistory = AttendanceHistory.from(localDateTime);
        assertThat(attendanceHistory.getAttendanceType()).isEqualTo(ATTENDANCE);
    }
}
