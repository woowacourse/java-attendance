package attendance.domain;

import static attendance.domain.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    @DisplayName("2024-12-2가 주어졌을 때, 월요일을 반환해야 한다.")
    @Test
    void given_2024_12_2_then_return_monday() {
        LocalDate findDate = LocalDate.of(2024, 12, 2);
        DayOfWeek dayOfWeek = findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(MONDAY);
    }

    @DisplayName("2024-12-3이 주어졌을 때, 화요일을 반환해야 한다.")
    @Test
    void given_2024_12_3_then_return_tuesday() {
        LocalDate findDate = LocalDate.of(2024, 12, 3);
        DayOfWeek dayOfWeek = DayOfWeek.findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(TUESDAY);
    }

}
