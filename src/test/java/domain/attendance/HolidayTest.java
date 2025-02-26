package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {
    @DisplayName("크리스마스는 공휴일로 판단한다")
    @Test
    void test() {
        // given
        LocalDateTime christmasDateTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        LocalDate christmasDate = LocalDate.of(2024, 12, 25);

        // when
        boolean isHoliday1 = Holiday.has(christmasDateTime.toLocalDate());
        boolean isHoliday2 = Holiday.has(christmasDate);

        // then
        Assertions.assertThat(isHoliday1).isTrue();
        Assertions.assertThat(isHoliday2).isTrue();
    }

    @DisplayName("새해는 공휴일로 판단한다")
    @Test
    void test2() {
        // given
        LocalDateTime christmasDateTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDate christmasDate = LocalDate.of(2025, 1, 1);

        // when
        boolean isHoliday1 = Holiday.has(christmasDateTime.toLocalDate());
        boolean isHoliday2 = Holiday.has(christmasDate);

        // then
        Assertions.assertThat(isHoliday1).isTrue();
        Assertions.assertThat(isHoliday2).isTrue();
    }
}