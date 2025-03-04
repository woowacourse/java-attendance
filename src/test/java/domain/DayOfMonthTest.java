package domain;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DayOfMonthTest {
    @ParameterizedTest
    @DisplayName("0 이하, 32이상의 숫자가 들어오면 오류가 발생합니다.")
    @ValueSource(ints = {-1, 0, 32})
    void InvalidDayOfMonthTest(int value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new DayOfMonth(value));
    }

    @ParameterizedTest
    @DisplayName("해당 날짜가 공휴일인지 확인합니다.")
    @ValueSource(ints = {1, 7, 8, 14, 15, 21, 22, 28, 25, 29})
    void isHolidayTest(int value) {
        LocalDate today = LocalDate.of(2024, 12, 2);
        Assertions.assertTrue(new DayOfMonth(value).isHoliday(today));
    }
}