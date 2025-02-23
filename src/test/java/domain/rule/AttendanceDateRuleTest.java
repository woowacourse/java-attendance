package domain.rule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceDateRuleTest {

    @Test
    @DisplayName("출석 가능 여부를 확인할 수 있다.")
    void canAttendDay() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 25);

        // when
        boolean isHoliday = AttendanceDateRule.canAttendDay(date);

        // then
        assertThat(isHoliday).isFalse();
    }

    @Test
    @DisplayName("공휴일을 확인할 수 있다.")
    void isHoliday() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 25);

        // when
        boolean isHoliday = AttendanceDateRule.isHoliday(date);

        // then
        assertThat(isHoliday).isTrue();
    }

    @Test
    @DisplayName("주말을 확인할 수 있다.")
    void isWeekend() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 15);

        // when
        boolean isWeekend = AttendanceDateRule.isWeekend(date.getDayOfWeek());

        // then
        assertThat(isWeekend).isTrue();
    }

    @Test
    @DisplayName("특별히 등교가 늦은 날을 확인할 수 있다. (월요일)")
    void isSpecialDay() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 16);

        // when
        boolean isSpecialDay = AttendanceDateRule.isSpecialDay(date);

        // then
        assertThat(isSpecialDay).isTrue();
    }
}
