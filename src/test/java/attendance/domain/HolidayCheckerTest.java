package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class HolidayCheckerTest {

    @Test
    void 주말이면_true를_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 1);

        assertThat(HolidayChecker.check(localDate)).isTrue();
    }

    @Test
    void 공휴일이면_true를_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        assertThat(HolidayChecker.check(localDate)).isTrue();
    }

    @Test
    void 운영되는_평일이면_false를_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 2);

        assertThat(HolidayChecker.check(localDate)).isFalse();
    }

    @Test
    void 등교일이_아니면_예외를_반생한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        assertThatThrownBy(() -> HolidayChecker.validWeekDay(localDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }
}
