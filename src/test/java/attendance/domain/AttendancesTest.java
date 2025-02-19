package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 찾으려는_날짜를_입력하면_출석_기록을_찾아준다() {
        // given
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 19, 10, 0));
        Attendances attendances = new Attendances();
        attendances.addAttendance(attendance);

        // when & then
        assertThat(attendances.findAttendanceByLocalDate(LocalDate.of(2025, 2, 19))).isNotNull();
    }

}
