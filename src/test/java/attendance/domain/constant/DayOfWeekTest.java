package attendance.domain.constant;

import java.time.DayOfWeek;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DayOfWeekTest {

    @ParameterizedTest
    @CsvSource(value = {"MONDAY, 월요일", "TUESDAY, 화요일", "WEDNESDAY, 수요일", "THURSDAY, 목요일", "FRIDAY, 금요일", "SATURDAY, 토요일", "SUNDAY, 일요일"})
    void 올바른_상태_반환_검사(DayOfWeek dayOfWeekValue, String expectedResult) {

        // given
        Weekday weekday= Weekday.from(dayOfWeekValue);

        // when & then
        Assertions.assertThat(weekday.getDayOfWeek()).isEqualTo(expectedResult);
    }

}