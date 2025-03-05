package domain.attendance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static domain.attendance.Holiday.*;
import static org.assertj.core.api.Assertions.*;


class HolidayTest {
    @Nested
    class HolidayCheckTest {
        @DisplayName("공휴일인 경우 false를 반환한다,")
        @Test
        void holidayTest() {
            LocalDate holidayDate2024 = LocalDate.of(2024, 12, 25);
            LocalDate holidayDate2025 = LocalDate.of(2025, 12, 25);

            LocalDate isNotHolidayDate = LocalDate.of(2025, 12, 14);

            assertThat(isHoliday(holidayDate2024)).isTrue();
            assertThat(isHoliday(holidayDate2025)).isTrue();
            assertThat(isHoliday(isNotHolidayDate)).isFalse();
        }
    }
}
