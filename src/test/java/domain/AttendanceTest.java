package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceTest {
    @DisplayName("출석인지 확인한다.")
    @Test
    void test1() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 17);
        LocalTime time = LocalTime.of(13, 5);
        Attendance attendance = Attendance.of(date, time);

        // when
        AttendanceStatus status = attendance.getStatus();

        // then
        assertThat(status).isSameAs(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("지각인지 확인한다.")
    @Test
    void test2() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 17);
        LocalTime time = LocalTime.of(13, 30);
        Attendance attendance = Attendance.of(date, time);

        // when
        AttendanceStatus status = attendance.getStatus();

        // then
        assertThat(status).isSameAs(AttendanceStatus.LATE);
    }

    @DisplayName("결석인지 확인한다.")
    @Test
    void test3() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 17);
        LocalTime time = LocalTime.of(13, 31);
        Attendance attendance = Attendance.of(date, time);

        // when
        AttendanceStatus status = attendance.getStatus();

        // then
        assertThat(status).isSameAs(AttendanceStatus.ABSENCE);
    }
}
