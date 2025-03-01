package domain;

import fixture.LocalDateFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {
    @Test
    @DisplayName("공휴일이면 true를 반환한다")
    void isHoliday_true() {
        Assertions.assertThat(Holiday.isHoliday(LocalDateFixture.CHRISTMAS)).isTrue();
    }

    @Test
    @DisplayName("공휴일이 아니면 false를 반환한다")
    void isHoliday_false() {
        Assertions.assertThat(Holiday.isHoliday(LocalDateFixture.CHRISTMAS)).isFalse();
    }
}