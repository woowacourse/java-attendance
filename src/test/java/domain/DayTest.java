package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayTest {
    @Test
    @DisplayName("날짜를 받아서 휴일인지 여부를 알려준다")
    void isDayOffTest() {
        // given
        LocalDate sunday = LocalDate.of(2024, 12, 1);
        // when
        boolean isHoliday = Day.isDayOff(sunday);
        // then
        assertThat(isHoliday).isTrue();
    }

    @Test
    @DisplayName("날짜를 받아서 요일을 알려준다")
    void getDayTest() {
        // given
        LocalDate sunday = LocalDate.of(2024, 12, 1);
        // when
        Day day = Day.getDay(sunday);
        // then
        assertThat(day).isEqualTo(Day.SUNDAY);
    }

    @Test
    @DisplayName("법정 공휴일은 휴일로 반환한다")
    void checkCalendarHolidayTest() {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, 25);
        // when
        boolean isHoliday = Day.isDayOff(holiday);
        // then
        assertThat(isHoliday).isTrue();
    }
}
