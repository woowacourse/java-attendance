package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        attendanceHistoryManager.addAttendanceHistory(attendanceHistory);
        assertThatThrownBy(() -> attendanceHistoryManager.addAttendanceHistory(attendanceHistory))
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
        attendanceHistoryManager.addAttendanceHistory(attendanceHistory);
        Set<AttendanceHistory> result = attendanceHistoryManager.getAttendanceHistories();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(attendanceHistory);
    }

    @Test
    void modify_attendance_result() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        LocalDate localDate = LocalDate.of(2024, 12, 26);
        LocalTime localTime = LocalTime.of(11, 00);
        AttendanceHistory attendanceHistory = new AttendanceHistory(LocalDateTime.of(localDate, localTime), ABSENCE);
        attendanceHistoryManager.addAttendanceHistory(attendanceHistory);

        LocalTime modifyTime = LocalTime.of(10, 00);

        //when
        AttendanceHistory result = attendanceHistoryManager.modifyAttendanceResult(attendanceHistory, modifyTime);

        //then
        assertThat(attendanceHistory).isEqualTo(result);
        assertThat(result.getAttendanceTime()).isEqualTo(LocalDateTime.of(localDate, modifyTime));
        assertThat(attendanceHistory.getAttendanceType()).isEqualTo(ATTENDANCE);
    }

    @Test
    void get_attendance_result_exception() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        LocalDate localDate = LocalDate.of(2024, 12, 26);

        //when
        //then
        assertThatThrownBy(() -> attendanceHistoryManager.getAttendanceHistory(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재하지 않습니다.");
    }
}
