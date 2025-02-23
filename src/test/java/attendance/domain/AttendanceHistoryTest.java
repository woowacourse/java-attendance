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

    @DisplayName("날짜를 수정할 경우, 변경된 다른 객체가 생성되어야 한다.")
    @Test
    void modify_attendance_history_then_another_object_return() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 40);
        LocalDateTime modifyDateTime = LocalDateTime.of(2025, 2, 21, 10, 4);

        //when
        AttendanceHistory attendanceHistory = AttendanceHistory.from(localDateTime);
        AttendanceHistory modifiedAttendanceHistory = attendanceHistory.modify(modifyDateTime);

        //then
        assertThat(attendanceHistory.getAttendanceType()).isEqualTo(ABSENCE);
        assertThat(modifiedAttendanceHistory.getAttendanceType()).isEqualTo(ATTENDANCE);
        assertThat(attendanceHistory).isNotEqualTo(modifiedAttendanceHistory);
    }
}
