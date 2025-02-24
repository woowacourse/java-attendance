package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
        "2025,12,25,CHRISTMAS",
        "2025,12,26,NONE",
    })
    @DisplayName("공휴일이면 해당 공휴일에 맞는 enum을 반환한다")
    void fromTest(int year, int month, int day, String holidayName) {
        // when
        LocalDate date = LocalDate.of(year, month, day);

        // then
        assertThat(Holiday.from(date).name()).isEqualTo(holidayName);
    }
}
