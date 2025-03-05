package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampusHolidayTest {

    @CsvSource(value = {
            "1,1,true", "1,2,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_공휴일인지_알려준다(int month, int day, boolean expected) {
        LocalDate localDate = LocalDate.of(2025, month, day);

        assertThat(CampusHoliday.isExistsInPublicHolidays(localDate)).isEqualTo(expected);
    }

    @CsvSource(value = {
            "2,23,true", "2,24,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_주말인지_알려준다(int month, int day, boolean expected) {
        LocalDate localDate = LocalDate.of(2025, month, day);

        assertThat(CampusHoliday.isCampusClosingDay(localDate)).isEqualTo(expected);
    }

}
