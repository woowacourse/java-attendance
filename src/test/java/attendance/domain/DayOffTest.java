package attendance.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOffTest {

    @Test
    @DisplayName("주말 및 공휴일 여부를 판단한다")
    void isDayOffTest() {
        // when then
        assertSoftly(softly -> {
            softly.assertThat(DayOff.isDayOff(LocalDate.of(2025, 2, 22))).isTrue(); // SATURDAY
            softly.assertThat(DayOff.isDayOff(LocalDate.of(2025, 2, 23))).isTrue(); // SUNDAY
            softly.assertThat(DayOff.isDayOff(LocalDate.of(2025, 12, 25))).isTrue(); // CHRISTMAS
            softly.assertThat(DayOff.isDayOff(LocalDate.of(2025, 12, 26))).isFalse(); // NOTHING
        });
    }
}
