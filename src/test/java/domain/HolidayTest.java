package domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {
    @Test
    @DisplayName("공휴일이면 true를 반환한다")
    void isHoliday_true() {
        LocalDate christmas = LocalDate.of(2025, 12, 25);
        Assertions.assertThat(Holiday.isHoliday(christmas)).isTrue();
    }

    @Test
    @DisplayName("공휴일이 아니면 false를 반환한다")
    void isHoliday_false() {
        LocalDate christmas = LocalDate.of(2025, 12, 10);
        Assertions.assertThat(Holiday.isHoliday(christmas)).isFalse();
    }
}