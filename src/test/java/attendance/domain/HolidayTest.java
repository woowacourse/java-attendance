package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("공휴일 정보")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HolidayTest {
    @ParameterizedTest
    @CsvSource({
            "1, 1, true",
            "3, 1, true",
            "5, 5, true",
            "1, 2, false",
            "3, 2, false",
            "5, 10, false"

    })
    void 공휴일에_해당하는_날짜인지_판별한다(int month, int day, boolean expected) {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, day);

        boolean result = Holiday.isHoliday(date);

        assertThat(result).isEqualTo(expected);
    }


    @Test
    void Holiday를_LocalDate로_반환한다() {
        Holiday holiday = Holiday.CHRISTMAS;

        assertThat(holiday.toLocalDate()).isInstanceOf(LocalDate.class);
    }

}
