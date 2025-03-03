package attendance.domain;

import static attendance.domain.CampusOperatingRule.DEFAULT_LATE_THRESHOLD;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {

    @Test
    void 토요일이면_예외를_던진다() {
        LocalDate saturday = LocalDate.of(2024, 12, 14);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);

        assertThatThrownBy(() -> Attendance.from(saturday, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 일요일이면_예외를_던진다() {
        LocalDate sunday = LocalDate.of(2024, 12, 15);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);

        assertThatThrownBy(() -> Attendance.from(sunday, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크리스마스면_예외를_던진다() {
        LocalDate christmas = LocalDate.of(2024, 12, 25);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);

        assertThatThrownBy(() -> Attendance.from(christmas, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 평일이면_예외를_던지지_않는다() {
        LocalDate friday = LocalDate.of(2024, 12, 13);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);

        assertThatCode(() -> Attendance.from(friday, time))
                .doesNotThrowAnyException();
    }

    @Test
    void 같은_날이면_true를_반환한다() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);
        Attendance attendance = Attendance.from(date, time);

        assertThat(attendance.isEqualToDate(date)).isTrue();
    }

    @Test
    void 다른_날이면_false를_반환한다() {
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = DEFAULT_LATE_THRESHOLD.getTime().minusMinutes(1);
        Attendance attendance = Attendance.from(date, time);

        assertThat(attendance.isEqualToDate(LocalDate.of(2024, 12, 12))).isFalse();
    }
}
