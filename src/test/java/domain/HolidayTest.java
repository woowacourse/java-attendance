package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @Test
    void 날짜에_따라_공휴일_여부를_판별한다() {
        LocalDate newTearDayDate = LocalDate.of(2025, 1, 1);
        LocalDate dayDate = LocalDate.of(2025, 2, 28);

        assertThat(true).isEqualTo(Holiday.isHoliday(newTearDayDate));
        assertThat(false).isEqualTo(Holiday.isHoliday(dayDate));
    }

}