package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayCalendarTest {


    @DisplayName("휴일이면 예외 처리")
    @Test
    void processHolidays() {
        // given
        final LocalDate localDate = LocalDate.of(2024, 12, 25);

        // when
        // then
        assertThatThrownBy(() -> HolidayCalendar.validateHoliday(localDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("휴일이 아니면 예외 처리를 하지 않는다")
    @Test
    void processInvalidHolidays() {
        // given
        final LocalDate localDate = LocalDate.of(2024, 12, 26);

        // when
        // then
        assertThatCode(() -> HolidayCalendar.validateHoliday(localDate))
                .doesNotThrowAnyException();
    }
}
