package attendance.domain;

import static attendance.domain.CampusOperatingRule.DEFAULT_ABSENCE_THRESHOLD;
import static attendance.domain.CampusOperatingRule.DEFAULT_LATE_THRESHOLD;
import static attendance.domain.CampusOperatingRule.MONDAY_ABSENCE_THRESHOLD;
import static attendance.domain.CampusOperatingRule.MONDAY_LATE_THRESHOLD;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 날짜가_같다면_true를_반환한다() {
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59));

        final var result = attendance.isEqualToDate(LocalDate.of(2024, 12, 13));

        assertThat(result).isTrue();
    }

    @Test
    void 날짜가_다르면_false를_반환한다() {
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59));

        final var result = attendance.isEqualToDate(LocalDate.of(2024, 12, 14));

        assertThat(result).isFalse();
    }

    @Test
    void 시간을_입력_받아_출석을_수정한다() {
        Attendance before = Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59));
        LocalTime time = LocalTime.of(10, 6);

        before.updateTime(time);
        Attendance updated = Attendance.from(LocalDateTime.of(2024, 12, 13, 10, 6));

        assertThat(before.getAttendanceDateTime()).isEqualTo(updated.getAttendanceDateTime());
    }

    @Test
    void 월요일_출결_상태를_반환한다_출석() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime dateTime = LocalDateTime.of(monday, MONDAY_LATE_THRESHOLD.getTime().minusMinutes(1));
        Attendance attend = Attendance.from(dateTime);

        final var result = attend.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 월요일_출결_상태를_반환한다_지각() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime dateTime = LocalDateTime.of(monday, MONDAY_LATE_THRESHOLD.getTime().plusMinutes(1));
        Attendance attend = Attendance.from(dateTime);

        final var result = attend.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 월요일_출결_상태를_반환한다_결석() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime date = LocalDateTime.of(monday, MONDAY_ABSENCE_THRESHOLD.getTime().plusMinutes(1));
        Attendance attend = Attendance.from(
                date);

        final var result = attend.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 다른_요일_출결_상태를_반환한다_출석() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalDateTime dateTime = LocalDateTime.of(date, DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1));
        Attendance attend = Attendance.from(dateTime);

        final var result = attend.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 다른_요일_출결_상태를_반환한다_지각() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalDateTime dateTime = LocalDateTime.of(date, DEFAULT_LATE_THRESHOLD.getTime().plusMinutes(1));
        Attendance attend = Attendance.from(dateTime);

        final var result = attend.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 다른_요일_출결_상태를_반환한다_결석() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalDateTime dateTime = LocalDateTime.of(date, DEFAULT_ABSENCE_THRESHOLD.getTime().plusMinutes(1));
        Attendance attend = Attendance.from(dateTime);

        final var result = attend.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
