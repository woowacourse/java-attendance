package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTimesTest {

    @Test
    @DisplayName("출석 시간이 주어졌을 때, 저장할 수 있어야 한다.")
    void given_nickname_and_attendance_time_then_save() {
        AttendanceTimes attendanceManager = AttendanceTimes.create();
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        attendanceManager.add(AttendanceTime.from(attendanceDateTime));
        Set<AttendanceTime> attendanceTimes = attendanceManager.getAttendanceTimes();
        assertThat(attendanceTimes.size()).isEqualTo(1);
    }
}
