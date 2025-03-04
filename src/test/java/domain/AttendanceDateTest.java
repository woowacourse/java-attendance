package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    @Test
    void 출석_일자가_존재하는지_확인한다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 10);

        AttendanceDate attendanceDate = new AttendanceDate(dateTime);

        assertThat(attendanceDate.isSameDate(dateTime.toLocalDate())).isTrue();
    }

    @Test
    void 출석_일자가_일치하는지_확인한다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 10);

        AttendanceDate attendanceDate = new AttendanceDate(dateTime);

        assertThat(attendanceDate.getDate()).isEqualTo(dateTime.toLocalDate());
    }

    @Test
    void 출석_시간이_일치하는지_확인한다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 10);

        AttendanceDate attendanceDate = new AttendanceDate(dateTime);

        assertThat(attendanceDate.getTime()).isEqualTo(dateTime.toLocalTime());
    }

    @Test
    void 출석_상태가_정상적으로_계산된다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 10);

        AttendanceDate attendanceDate = new AttendanceDate(dateTime);
        AttendanceStatus status = AttendanceStatus.evaluateAttendance(dateTime);

        assertThat(attendanceDate.getStatus()).isEqualTo(status);
    }
}
