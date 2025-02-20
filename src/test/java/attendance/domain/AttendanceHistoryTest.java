package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {
    @Test
    void create() {
        AttendanceHistory result = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        assertThat(result).isNotNull();
        assertThat(result.getAttendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 26, 10, 00));
        assertThat(result.getAttendanceType()).isEqualTo(ATTENDANCE);
    }

    @Test
    void modify_attendance_result() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 11, 00),
                ABSENCE
        );
        AttendanceHistory modifyAttendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        //when
        attendanceHistory.modify(modifyAttendanceHistory);

        //then
        assertThat(attendanceHistory).isEqualTo(modifyAttendanceHistory);
        assertThat(attendanceHistory.getAttendanceTime()).isEqualTo(modifyAttendanceHistory.getAttendanceTime());
        assertThat(attendanceHistory.getAttendanceType()).isEqualTo(ATTENDANCE);
    }
}
