package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @CsvSource(value = {
            "1,1,true", "3,1,true", "5,5,true", "6,6,true", "8,15,true", "10,3,true", "10,9,true", "12,25,true",
            "1,2,false"
    })
    @ParameterizedTest
    void 날자를_알려주면_공휴일인지_알려준다(int month, int day, boolean expected) {
        LocalDate localDate = LocalDate.of(2025, month, day);

        assertThat(Holiday.isExists(localDate)).isEqualTo(expected);
    }

}
