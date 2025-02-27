package attendance.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DayOffTest {

    @ParameterizedTest
    @CsvSource({
        "2025,2,22,true", // SATURDAY
        "2025,2,23,true", // SUNDAY
        "2025,12,25,true", // CHRISTMAS
        "2025,12,26,false", // NOTHING
    })
    @DisplayName("주말 및 공휴일 여부를 판단한다")
    void isDayOffTest(int year, int month, int day, boolean expected) {
        // when then
        assertSoftly(softly -> {
            softly.assertThat(DayOff.isDayOff(LocalDate.of(year, month, day))).isEqualTo(expected);
        });
    }
}
