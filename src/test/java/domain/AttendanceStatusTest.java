package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import global.util.Date;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @Test
    void 출석을_확인한다() {
        LocalTime targetTime = LocalTime.of(10, 3, 0);
        LocalDateTime targetDate = LocalDateTime.of(Date.TODAY.toLocalDate(), targetTime);

        assertThat(AttendanceStatus.attend(targetDate)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 지각을_확인한다() {
        LocalTime targetTime = LocalTime.of(10, 6, 0);
        LocalDateTime targetDate = LocalDateTime.of(Date.TODAY.toLocalDate(), targetTime);

        assertThat(AttendanceStatus.attend(targetDate)).isEqualTo(AttendanceStatus.TARDY);
    }

    @Test
    void 지각을_확인한다2() {
        LocalTime targetTime = LocalTime.of(10, 30, 0);
        LocalDateTime targetDate = LocalDateTime.of(Date.TODAY.toLocalDate(), targetTime);

        assertThat(AttendanceStatus.attend(targetDate)).isEqualTo(AttendanceStatus.TARDY);
    }

    @Test
    void 결석을_확인한다() {
        LocalTime targetTime = LocalTime.of(10, 31, 0);
        LocalDateTime targetDate = LocalDateTime.of(Date.TODAY.toLocalDate(), targetTime);

        assertThat(AttendanceStatus.attend(targetDate)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 등교일이_아닐때_출석시_예외를_던진다() {
        assertThatThrownBy(() -> {
            AttendanceStatus.attend(LocalDateTime.of(2024, 12, 25, 10, 30));
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            AttendanceStatus.attend(LocalDateTime.of(2024, 12, 1, 10, 30));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 월요일일때_출석을_확인한다() {
        LocalTime targetTime = LocalTime.of(13, 0, 0);
        LocalDateTime targetDate = LocalDateTime.of(LocalDate.of(2024, 12, 2), targetTime);

        assertThat(AttendanceStatus.attend(targetDate)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }
}
