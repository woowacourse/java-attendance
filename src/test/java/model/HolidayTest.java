package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @Test
    @DisplayName("공휴일일 경우 true를 반환한다.")
    void test1() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        // when
        boolean result = Holiday.isHoliday(localDate);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("공휴일이 아닐 경우 false를 반환한다.")
    void test2() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 24);

        // when
        boolean result = Holiday.isHoliday(localDate);

        // then
        assertThat(result).isFalse();
    }
}
