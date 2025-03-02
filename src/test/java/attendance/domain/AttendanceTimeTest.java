package attendance.domain;

import static attendance.domain.CampusOperatingRule.CAMPUS_CLOSE_HOUR;
import static attendance.domain.CampusOperatingRule.CAMPUS_OPEN_HOUR;
import static attendance.domain.CampusOperatingRule.DEFAULT_ABSENCE_THRESHOLD;
import static attendance.domain.CampusOperatingRule.DEFAULT_LATE_THRESHOLD;
import static attendance.domain.CampusOperatingRule.MONDAY_ABSENCE_THRESHOLD;
import static attendance.domain.CampusOperatingRule.MONDAY_LATE_THRESHOLD;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @Test
    void _08시_이전에는_출석할_수_없다() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = CAMPUS_OPEN_HOUR.getTime().minusMinutes(1);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertThatThrownBy(() -> Attendance.from(dateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _23시_이후에는_출석할_수_없다() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = CAMPUS_CLOSE_HOUR.getTime().plusMinutes(1);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertThatThrownBy(() -> Attendance.from(dateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _캠퍼스_운영시간_내에는_출석_가능하다() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = CAMPUS_OPEN_HOUR.getTime().plusMinutes(1);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertThatCode(() -> Attendance.from(dateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 월요일에_13시_5분_초과는_지각이다() {
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = MONDAY_LATE_THRESHOLD.getTime().plusMinutes(1);
        Attendance attendance = Attendance.from(LocalDateTime.of(date, time));

        AttendanceStatus result = attendance.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 월요일에_13시_30분_초과는_결석이다() {
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = MONDAY_ABSENCE_THRESHOLD.getTime().plusMinutes(1);
        Attendance attendance = Attendance.from(LocalDateTime.of(date, time));

        AttendanceStatus result = attendance.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일에_13시_5분_이전이면_정상_출석이다() {
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = MONDAY_LATE_THRESHOLD.getTime().minusMinutes(1);
        Attendance attendance = Attendance.from(LocalDateTime.of(date, time));

        AttendanceStatus result = attendance.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 다른_요일에_10시_5분_초과는_지각이다() {
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().plusMinutes(1);
        Attendance attendance = Attendance.from(LocalDateTime.of(date, time));

        AttendanceStatus result = attendance.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 다른_요일에_10시_30분_초과는_결석이다() {
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = DEFAULT_ABSENCE_THRESHOLD.getTime().plusMinutes(1);
        Attendance attendance = Attendance.from(LocalDateTime.of(date, time));

        AttendanceStatus result = attendance.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 다른_요일에_10시_5분_이전이면_정상_출석이다() {
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);
        Attendance attendance = Attendance.from(LocalDateTime.of(date, time));

        AttendanceStatus result = attendance.checkAttendanceStatus();

        assertThat(result).isEqualTo(AttendanceStatus.ATTEND);
    }
}
