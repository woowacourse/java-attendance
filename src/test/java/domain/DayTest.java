package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DayTest {
    @Test
    @DisplayName("날짜를 받아서 휴일인지 여부를 알려준다")
    void checkHolidayTest() {
        boolean isHoliday = Day.checkHoliday(LocalDate.of(2024, 12, 1));

        assertThat(isHoliday).isTrue();
    }

    @Test
    @DisplayName("날짜를 받아서 요일을 알려준다")
    void getDayTest() {
        Day day = Day.getDay(LocalDate.of(2024, 12, 1));

        assertThat(day).isEqualTo(Day.SUNDAY);
    }

    @Test
    @DisplayName("법정 공휴일은 휴일로 반환한다")
    void checkCalendarHolidayTest() {
        boolean isHoliday = Day.checkHoliday(LocalDate.of(2024, 12, 25));

        assertThat(isHoliday).isTrue();
    }
}
