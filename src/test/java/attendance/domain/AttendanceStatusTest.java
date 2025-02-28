package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceStatusTest {

    @Test
    @DisplayName("출석 목록으로 출결 상황을 종합한다")
    void 출석_목록으로_출결_상황을_종합한다() {
        // given
        LocalDate nowDate = LocalDate.now();
        List<Attendance> attendances = List.of(
                Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.of(10, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(10, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(10, 0)))
        );

        // when
        AttendanceStatus result = AttendanceStatus.fromAttendances(attendances);

        // then
        assertAll(
                () -> assertThat(result.getStateCount(AttendanceState.ATTENDANCE)).isEqualTo(3),
                () -> assertThat(result.getStateCount(AttendanceState.TARDY)).isEqualTo(0),
                () -> assertThat(result.getStateCount(AttendanceState.ABSENCE)).isEqualTo(0)
        );
    }
}
