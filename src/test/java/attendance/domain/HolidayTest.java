package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.MonthDay;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-25, true",
            "2024-12-26, false",
    })
    void 공휴일인지_확인할_수_있다(LocalDate date, boolean expected) {
        // when
        boolean actual = Holiday.isHoliday(MonthDay.from(date));

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
