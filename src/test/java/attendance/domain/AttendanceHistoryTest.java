package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {
    @Test
    void create() {
        AttendanceHistory result = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                ATTENDANCE
        );

        assertThat(result).isNotNull();
        assertThat(result.getAttendanceDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 26, 10, 00));
        assertThat(result.getAttendanceType()).isEqualTo(ATTENDANCE);
    }

    @Test
    void modify_attendance_result() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 26);
        LocalTime localTime = LocalTime.of(11, 00);
        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
        AttendanceHistory attendanceHistory = new AttendanceHistory(dateTime, ABSENCE);

        LocalTime modifyTime = LocalTime.of(10, 00);
        AttendanceType modifyAttendanceType = AttendancePolicy.checkAttendanceType(dateTime.toLocalDate(), modifyTime);

        //when
        attendanceHistory.modify(modifyTime, modifyAttendanceType);

        //then
        assertThat(attendanceHistory.getAttendanceDateTime()).isEqualTo(LocalDateTime.of(localDate, modifyTime));
        assertThat(attendanceHistory.getAttendanceType()).isEqualTo(modifyAttendanceType);
    }

    @DisplayName("주어진_날짜와_출석_날짜가_같은지_여부를_반환할_수_있다")
    @Test
    void 주어진_날짜와_출석_날짜가_같은지_여부를_반환할_수_있다() {
        //given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(11, 00);
        AttendanceHistory attendanceHistory = new AttendanceHistory(
                LocalDateTime.of(attendanceDate, attendanceTime),
                ATTENDANCE
        );

        //when
        boolean result = attendanceHistory.isAttendanceDateEquals(attendanceDate);

        //then
        assertThat(result).isTrue();
    }
}
