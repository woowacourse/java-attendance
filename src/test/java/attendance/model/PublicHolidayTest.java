package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("법정 공휴일 테스트")
class PublicHolidayTest {

    @DisplayName("법정 공휴일인지 아닌지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "2024-12-24, false",
            "2024-12-25, true"
    })
    void isPublicHolidayTest(@JavaTimeConversionPattern("yyyy-MM-dd") LocalDate date, boolean expected) {
        // when
        boolean isPublicHoliday = PublicHoliday.isPublicHoliday(date);

        // then
        assertThat(isPublicHoliday)
                .isEqualTo(expected);
    }
}
