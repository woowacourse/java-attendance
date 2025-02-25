package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @DisplayName("공휴일인지 알 수 있다.")
    @Test
    void isHoliday() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 25);

        //when
        boolean result = Holiday.isHoliday(date);

        //then
        assertThat(result).isTrue();
    }
}
