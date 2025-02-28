package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {

    @Test
    @DisplayName("출석 시간이 주어졌을 때, 저장할 수 있어야 한다.")
    void given_nickname_and_attendance_time_then_save() {
        AttendanceManager attendanceManager = AttendanceManager.create();
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        attendanceManager.add(AttendanceTime.from(attendanceDateTime));
        List<AttendanceTime> attendanceTimes = attendanceManager.getAttendanceTimes();
        assertThat(attendanceTimes.size()).isEqualTo(1);
    }

}
