package util;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

    @Test
    @DisplayName("시간(시분)과 시간 사이를 구분할 수 있다")
    void IsInRangeTest() {
        // given
        LocalTime startTime = LocalTime.of(1, 0);
        LocalTime endTime = LocalTime.of(2, 31);

        LocalTime earlyTime = LocalTime.of(0, 59);
        LocalTime betweenTime = LocalTime.of(2, 30);
        LocalTime overTime = LocalTime.of(2, 32);

        // when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(DateTimeUtil.isInRange(startTime, endTime, earlyTime)).isFalse();
            softAssertions.assertThat(DateTimeUtil.isInRange(startTime, endTime, betweenTime)).isTrue();
            softAssertions.assertThat(DateTimeUtil.isInRange(startTime, endTime, overTime)).isFalse();
        });
    }

    @Test
    @DisplayName("시작 시간이 종료 시간보다 뒤인 경우 예외가 발생한다")
    void IsInRangeTest_Exception() {
        // given
        LocalTime startTime = LocalTime.of(3, 0);
        LocalTime endTime = LocalTime.of(2, 31);

        LocalTime betweenTime = LocalTime.of(2, 30);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            DateTimeUtil.isInRange(startTime, endTime, betweenTime);
        }).isInstanceOf(IllegalArgumentException.class);
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

        // Holiday
        public static LocalDate CHRISTMAS = LocalDate.of(2025, 12, 25);
    }
}