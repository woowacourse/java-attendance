package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryManagerTest {

    @Test
    void validate_duplicated_attendance() {
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        AttendanceHistory attendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );
        attendanceHistoryManager.addAttendanceResult(attendanceHistory);
        assertThatThrownBy(() -> attendanceHistoryManager.addAttendanceResult(attendanceHistory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 이미 출석하셨습니다.");
    }

    @Test
    void add_attendance_history() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        AttendanceHistory attendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        //when
        attendanceHistoryManager.addAttendanceResult(attendanceHistory);
        Set<AttendanceHistory> result = attendanceHistoryManager.getAttendanceHistories();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(attendanceHistory);
    }

    @Test
    void modify_attendance_result() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        AttendanceHistory beforeAttendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 15, 00),
                ABSENCE
        );
        attendanceHistoryManager.addAttendanceResult(beforeAttendanceHistory);
        AttendanceHistory afterAttendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        //when
        attendanceHistoryManager.modifyAttendanceResult(afterAttendanceHistory);

        //then
        assertThat(beforeAttendanceHistory).isEqualTo(afterAttendanceHistory);
        assertThat(beforeAttendanceHistory.getAttendanceTime()).isEqualTo(afterAttendanceHistory.getAttendanceTime());
        assertThat(beforeAttendanceHistory.getAttendanceType()).isEqualTo(afterAttendanceHistory.getAttendanceType());
    }

    @Test
    void get_attendance_result_exception() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        AttendanceHistory attendanceHistory = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 15, 00),
                ABSENCE
        );

        //when
        //then
        assertThatThrownBy(() -> attendanceHistoryManager.getAttendanceHistory(attendanceHistory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재하지 않습니다.");
    }
}
