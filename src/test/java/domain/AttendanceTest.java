package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceTest {

    @Test
    void 출석시간을_통해_출석체크한다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), attendanceTime);

        assertThatCode(() -> new Attendance(localDateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 토요일_출석은_예외를_발생시킨다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDate saturday = LocalDate.of(2025, 3, 1);
        LocalDateTime localDateTime = LocalDateTime.of(saturday, attendanceTime);

        assertThatThrownBy(() -> new Attendance(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] 주말은 등교일이 아닙니다.");
    }

    @Test
    void 일요일_출석은_예외를_발생시킨다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDate sunday = LocalDate.of(2025, 3, 2);
        LocalDateTime localDateTime = LocalDateTime.of(sunday, attendanceTime);

        assertThatThrownBy(() -> new Attendance(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] 주말은 등교일이 아닙니다.");
    }

}
