package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AttendancesTest {

    @Test
    @DisplayName("날짜와 시간으로 출석을 등록한다")
    void 날짜와_시간으로_출석을_등록한다() {
        // given
        Attendance defaultAttendance = new Attendance(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Attendances attendances = new Attendances(List.of(defaultAttendance));

        LocalDateTime attendanceDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));

        // when
        Attendances newAttendances = attendances.processCheck(attendanceDateTime);
        Attendance result = newAttendances.findAttendanceByDate(attendanceDateTime.toLocalDate());

        // then
        assertThat(result.getDateTime())
                .isEqualTo(attendanceDateTime);
    }
}
