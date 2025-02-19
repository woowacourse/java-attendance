package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    void validate_duplicated_attendance() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        AttendanceResult attendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );
        attendanceHistory.addAttendanceResult(attendanceResult);
        assertThatThrownBy(() -> attendanceHistory.addAttendanceResult(attendanceResult))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 이미 출석하셨습니다.");
    }

    @Test
    void add_attendance_history() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        AttendanceResult attendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        //when
        attendanceHistory.addAttendanceResult(attendanceResult);
        Set<AttendanceResult> result = attendanceHistory.getAttendanceHistory();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(attendanceResult);
    }

    @Test
    void modify_attendance_result() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        AttendanceResult beforeAttendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 15, 00),
                ABSENCE
        );
        attendanceHistory.addAttendanceResult(beforeAttendanceResult);
        AttendanceResult afterAttendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        //when
        attendanceHistory.modifyAttendanceResult(afterAttendanceResult);

        //then
        assertThat(beforeAttendanceResult).isEqualTo(afterAttendanceResult);
        assertThat(beforeAttendanceResult.getAttendanceTime()).isEqualTo(afterAttendanceResult.getAttendanceTime());
        assertThat(beforeAttendanceResult.getAttendanceType()).isEqualTo(afterAttendanceResult.getAttendanceType());
    }

    @Test
    void get_attendance_result_exception() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        AttendanceResult attendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 15, 00),
                ABSENCE
        );

        //when
        //then
        assertThatThrownBy(() -> attendanceHistory.getAttendanceResult(attendanceResult))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재하지 않습니다.");
    }
}
