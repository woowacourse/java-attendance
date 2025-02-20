package attendance.domain.constant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DayOfWeekTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 월요일", "2, 화요일", "3, 수요일", "4, 목요일", "5, 금요일", "6, 토요일", "7, 일요일"})
    void 올바른_상태_반환_검사(int dayOfWeekValue, String expectedResult) {

        // given
        DayOfWeek dayOfWeek = DayOfWeek.from(dayOfWeekValue);

        // when & then
        Assertions.assertThat(dayOfWeek.getDayOfWeek()).isEqualTo(expectedResult);
    }

}