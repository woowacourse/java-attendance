package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class MonthTest {
    @DisplayName("12월은 크리스마스를 휴일로 판단할 수 있다.")
    @Test
    void test1() {
        // given
        Month month = Month.DECEMBER;

        // when
        final boolean isHoliday = month.isHoliday(25);

        // then
        assertThat(isHoliday).isTrue();
    }

    @DisplayName("토요일은 휴일로 판단할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {7, 14, 21, 28})
    void test2(int day) {
        // given
        Month month = Month.DECEMBER;

        // when
        final boolean isHoliday = month.isHoliday(day);

        // then
        assertThat(isHoliday).isTrue();
    }

    @DisplayName("일요일은 휴일로 판단할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 8, 15, 22, 29})
    void test3(int day) {
        // given
        Month month = Month.DECEMBER;

        // when
        final boolean isHoliday = month.isHoliday(day);

        // then
        assertThat(isHoliday).isTrue();
    }
}
