package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCalculatorTest {

    @DisplayName("2024-12-2가 주어졌을 때, 월요일을 반환해야 한다.")
    @Test
    void given_2024_12_2_then_return_monday() {
        String expectedDayOfWeek = "월요일";
        LocalDate findDate = LocalDate.of(2024, 12, 2);
        String dayOfWeek = AttendanceCalculator.findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(expectedDayOfWeek);
    }

    @DisplayName("2024-12-3이 주어졌을 때, 화요일을 반환해야 한다.")
    @Test
    void given_2024_12_3_then_return_tuesday() {
        String expectedDayOfWeek = "화요일";
        LocalDate findDate = LocalDate.of(2024, 12, 3);
        String dayOfWeek = AttendanceCalculator.findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(expectedDayOfWeek);
    }

}
