package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceTest {
    @DisplayName("출석인지 확인한다.")
    @Test
    void test1() {
        // given
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 17, 13, 5));

        // when
        AttendanceStatus status = attendance.getStatus();

        // then
        assertThat(status).isSameAs(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("지각인지 확인한다.")
    @Test
    void test2() {
        // given
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 17, 13, 30));

        // when
        AttendanceStatus status = attendance.getStatus();

        // then
        assertThat(status).isSameAs(AttendanceStatus.LATE);
    }

    @DisplayName("결석인지 확인한다.")
    @Test
    void test3() {
        // given
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 17, 13, 31));

        // when
        AttendanceStatus status = attendance.getStatus();

        // then
        assertThat(status).isSameAs(AttendanceStatus.ABSENCE);
    }
}
