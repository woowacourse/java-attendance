import static org.assertj.core.api.Assertions.*;

import domain.Holiday;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HolidayTest {

    @DisplayName("크리스마스를 확인한다.")
    @Test
    void isHoliday_1() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        assertThat(Holiday.isWeekDay(date)).isFalse();
    }

    @DisplayName("크리스마스가 아닌지 확인한다.")
    @Test
    void isHoliday_2() {
        LocalDate date = LocalDate.of(2024, 12, 24);
        assertThat(Holiday.isWeekDay(date)).isTrue();
    }

    @DisplayName("주말을 확인한다.")
    @Test
    void isWeekend_1() {
        LocalDate date = LocalDate.of(2024, 12, 1);
        assertThat(Holiday.isWeekDay(date)).isFalse();
    }

    @DisplayName("주말이 아닌지 확인한다.")
    @Test
    void isWeekend_2() {
        LocalDate date = LocalDate.of(2024, 12, 2);
        assertThat(Holiday.isWeekDay(date)).isTrue();
    }
}