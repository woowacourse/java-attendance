package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HolidayTest {

    @DisplayName("공휴일이 주어졌을 때, true를 반환해야 한다.")
    @Test
    void given_holiday_then_return_true() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 25);
        boolean isHoliday = Holiday.isHoliday(attendanceDate);
        assertThat(isHoliday).isTrue();
    }

    @DisplayName("공휴일이 아닐 경우, false를 반환해야 한다.")
    @Test
    void given_weekday_then_return_false() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 24);
        boolean isHoliday = Holiday.isHoliday(attendanceDate);
        assertThat(isHoliday).isFalse();
    }

}
