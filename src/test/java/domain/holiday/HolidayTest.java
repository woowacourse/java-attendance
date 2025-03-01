package domain.holiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @Test
    @DisplayName("등록된 공휴일 입력 시 true 반환")
    void holidayTrueTest() {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        // when
        boolean b = Holiday.isHoliday(holiday);

        // then
        assertThat(b).isTrue();
    }

    @Test
    @DisplayName("공휴일 아닌 값 입력 시 false 반환")
    void holidayFalseTest() {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, 26);

        // when
        boolean b = Holiday.isHoliday(holiday);

        // then
        assertThat(b).isFalse();
    }
}