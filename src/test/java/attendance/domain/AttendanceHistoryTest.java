package attendance.domain;

import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {
    @DisplayName("출석_시간과_출석_타입으로_Attendance_객체를_생성할_수_있다")
    @Test
    void create() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 26, 10, 00);
        AttendanceType attendanceType = ATTENDANCE;

        //when
        AttendanceHistory result = new AttendanceHistory(attendanceDateTime, attendanceType);

        //then
        assertThat(result.getAttendanceDateTime()).isEqualTo(attendanceDateTime);
        assertThat(result.getAttendanceType()).isEqualTo(attendanceType);
    }

    @DisplayName("주어진_날짜와_출석_날짜가_같은지_여부를_반환할_수_있다")
    @Test
    void isAttendanceDateEquals() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 26, 11, 00);
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendanceDateTime, ATTENDANCE);

        //when
        boolean result = attendanceHistory.isAttendanceDateEquals(attendanceDateTime.toLocalDate());

        //then
        assertThat(result).isTrue();
    }
}
