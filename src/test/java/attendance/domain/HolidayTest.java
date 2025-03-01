package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "12, 3, false",
            "12, 25, true"
    })
    void 공휴일인지_확인한다(final int month, final int day, final boolean expected) {
        assertThat(Holiday.isHoliday(month, day)).isEqualTo(expected);
    }
}
