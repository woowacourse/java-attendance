package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "2024, 12, 1, true",
            "2024, 12, 25, true",
            "2024, 12, 2, false",
    })
    void 휴일_여부를_알_수_있다(int year, int month, int day, boolean expected) {
        //given
        LocalDate date = LocalDate.of(year, month, day);
        //when
        boolean actual = Holiday.isHolidayOrWeekend(date);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
