package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "2024, 12, 1, true",
            "2024, 12, 2, false",
            "2024, 12, 25, true",
    })
    void 특정_날짜가_휴일인지_아닌지_결정한다(int year, int month, int date, boolean expected) {
        //given
        LocalDate day = LocalDate.of(year, month, date);
        //when
        boolean actual = Holiday.isHoliday(day);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
