package test.model.date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import model.date.December;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecemberTest {

    @DisplayName("등교일이 아닌 경우에 예외를 반환한다. - 공휴일")
    @Test
    void fail_ifAttendanceDateIsHoliday() {
        assertThatThrownBy(() -> December.validateHoliday(LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("등교일이 아닌 경우에 예외를 반환한다. - 주말")
    @Test
    void fail_ifAttendanceDateIsWeekend() {
        assertThatThrownBy(() -> December.validateHoliday(LocalDate.of(2024, 12, 14)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> December.validateHoliday(LocalDate.of(2024, 12, 15)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
