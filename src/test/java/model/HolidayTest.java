package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @DisplayName("공휴일인지 확인한다")
    @Test
    void holidayTest() {
        LocalDate holiday = LocalDate.of(2024, 12, 25);
        LocalDate notHoliday = LocalDate.of(2024, 12, 3);
        assertAll(
                () -> assertThat(Holiday.isHoliday(holiday)).isTrue(),
                () -> assertThat(Holiday.isHoliday(notHoliday)).isFalse()
        );
    }
}
