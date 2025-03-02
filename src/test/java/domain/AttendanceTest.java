package domain;

import domain.constant.StandardDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class AttendanceTest {

    @Test
    void 출석시간을_통해_출석체크한다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDateTime localDateTime = LocalDateTime.of(StandardDate.TODAY, attendanceTime);

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

    @Test
    void 수정일자에_따라_출석을_수정한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 27, 10, 0));

        LocalDateTime localDateTime = attendance.updateAttendance(LocalTime.of(10, 6));
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();

        assertThat(localDate).isEqualTo(LocalDate.of(2025, 2, 27));
        assertThat(localTime).isEqualTo(LocalTime.of(10, 6));
    }

    @Test
    void 주말의_경우_예외를_발생시킨다() {
        assertThatThrownBy(() ->
                new Attendance(LocalDateTime.of(2024, 12, 28, 10, 0)));
    }

    @Test
    void 날짜를_같으면_TRUE를_반환한다() {
        LocalDate date = LocalDate.of(2025, 2, 28);
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 28, 10, 0));

        boolean isEqualDate = attendance.isEqualTo(date);

        assertThat(isEqualDate).isEqualTo(true);
    }

    @Test
    void 날짜가_다르면_FALSE를_반환한다() {
        LocalDate date = LocalDate.of(2025, 2, 25);
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 28, 10, 0));

        boolean isEqualDate = attendance.isEqualTo(date);

        assertThat(isEqualDate).isEqualTo(false);
    }
}
