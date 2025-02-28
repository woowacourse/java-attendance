package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AttendanceStatusTest {

    @Test
    void 실행기준시각의_출석을_확인한다() {
        assertThat(AttendanceStatus.evaluateAttendanceNow()).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 월요일_13시_35분은_결석한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 9, 13, 35, 0);

        assertThat(AttendanceStatus.evaluateAttendance(dateTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일_13시_06분은_지각한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 9, 13, 6, 0);

        assertThat(AttendanceStatus.evaluateAttendance(dateTime)).isEqualTo(AttendanceStatus.TARDY);
    }

    @Test
    void 월요일_12시_55분은_출석한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 9, 12, 55, 0);

        assertThat(AttendanceStatus.evaluateAttendance(dateTime)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 월요일이_아닌_날_10시_35분은_결석한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 35, 0);

        assertThat(AttendanceStatus.evaluateAttendance(dateTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일이_아닌_날_10시_06분은_지각한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 6, 0);

        assertThat(AttendanceStatus.evaluateAttendance(dateTime)).isEqualTo(AttendanceStatus.TARDY);
    }

    @Test
    void 월요일이_아닌_날_9시_55분은_출석한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 9, 55, 0);

        assertThat(AttendanceStatus.evaluateAttendance(dateTime)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }
}
