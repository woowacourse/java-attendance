package util;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeUtilTest {
    @Test
    @DisplayName("해당 날짜가 주말이면 true를 반환한다.")
    void isWeekendTest_true() {
        // when & then
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.SATURDAY)).isTrue();
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.SUNDAY)).isTrue();
    }

    @Test
    @DisplayName("해당 날짜가 주말이 아니면 false를 반환한다.")
    void isWeekendTest_false() {
        // when & then
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.MONDAY)).isFalse();
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.TUESDAY)).isFalse();
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.WEDNESDAY)).isFalse();
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.THURSDAY)).isFalse();
        Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.FRIDAY)).isFalse();
    }

    static class LocalDateFixture {
        // DayOfWeek
        public static LocalDate MONDAY = LocalDate.of(2025, 2, 3);
        public static LocalDate TUESDAY = LocalDate.of(2025, 2, 4);
        public static LocalDate WEDNESDAY = LocalDate.of(2025, 2, 5);
        public static LocalDate THURSDAY = LocalDate.of(2025, 2, 6);
        public static LocalDate FRIDAY = LocalDate.of(2025, 2, 7);
        public static LocalDate SATURDAY = LocalDate.of(2025, 2, 8);
        public static LocalDate SUNDAY = LocalDate.of(2025, 2, 9);
    }
}