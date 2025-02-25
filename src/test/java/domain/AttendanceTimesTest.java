package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AttendanceTimesTest {

    @Test
    void 결석_수_계산() {
        AttendanceTimes attendanceTimes = new AttendanceTimes(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 30),
                LocalDateTime.of(2024, 12, 3, 10, 30),
                LocalDateTime.of(2024, 12, 4, 10, 30),
                LocalDateTime.of(2024, 12, 5, 10, 31))
                , LocalDate.of(2024, 12, 6));

        assertThat(attendanceTimes.getAbsentCount()).isEqualTo(2);
    }

    @Test
    void 지각_수_계산() {
        AttendanceTimes attendanceTimes = new AttendanceTimes(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 5),
                LocalDateTime.of(2024, 12, 3, 10, 6),
                LocalDateTime.of(2024, 12, 4, 10, 6),
                LocalDateTime.of(2024, 12, 5, 10, 30))
                , LocalDate.of(2024, 12, 6));

        assertThat(attendanceTimes.getLateCount()).isEqualTo(3);
    }

    @Test
    void 출석_상태_계산() {
        AttendanceTimes attendanceTimes = new AttendanceTimes(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 5),
                LocalDateTime.of(2024, 12, 4, 10, 6),
                LocalDateTime.of(2024, 12, 5, 10, 31))
                , LocalDate.of(2024, 12, 7));

        Map<AttendanceStatus, Integer> expected = new HashMap<>();
        expected.put(AttendanceStatus.ATTEND, 2);
        expected.put(AttendanceStatus.LATE, 1);
        expected.put(AttendanceStatus.ABSENT, 1);
        expected.put(AttendanceStatus.UNATTEND, 1);

        assertThat(attendanceTimes.calculateAttendanceStatuses()).containsAllEntriesOf(expected);
    }

    @Test
    void 출석_날짜인지_확인() {
        AttendanceTimes attendanceTimes = new AttendanceTimes(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 5),
                LocalDateTime.of(2024, 12, 4, 10, 6),
                LocalDateTime.of(2024, 12, 5, 10, 31))
                , LocalDate.of(2024, 12, 7));

        LocalDate attendDate = LocalDate.of(2024, 12, 2);

        assertThat(attendanceTimes.checkAttended(attendDate)).isEqualTo(true);
    }

    @Test
    void 미출석_날짜인지_확인() {
        AttendanceTimes attendanceTimes = new AttendanceTimes(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0))
                , LocalDate.of(2024, 12, 7));

        LocalDate unattendedDate = LocalDate.of(2024, 12, 10);

        assertThat(attendanceTimes.checkAttended(unattendedDate)).isEqualTo(false);
    }

    @Test
    void 출석하지_않은_날짜_수정_예외() {
        AttendanceTimes attendanceTimes = new AttendanceTimes(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0))
                , LocalDate.of(2024, 12, 7));

        assertThatThrownBy(() -> attendanceTimes.getAttendanceTime(LocalDate.of(2024, 12, 10)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
