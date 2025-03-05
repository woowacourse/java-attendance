package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    void 출석_시간을_저장할_수_있다() {
        // given
        LocalDate localDate = LocalDate.of(2025, 3, 5);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        Attendance attendance = new Attendance(localDate, localTime);

        // then
        Assertions.assertThat(attendance.getLocalDate()).isEqualTo(localDate);
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 출석_시간을_수정할_수_있다() {
        // given
        LocalDate localDate = LocalDate.of(2025, 3, 5);
        LocalTime beforeLocalTime = LocalTime.of(9, 55);
        LocalTime afterLocalTime = LocalTime.of(10, 55);
        Attendance attendance = new Attendance(localDate, beforeLocalTime);

        // when
        Attendance updateAttendance = attendance.updateTime(afterLocalTime);

        // then
        Assertions.assertThat(updateAttendance.getLocalTime()).isEqualTo(afterLocalTime);
    }
}
