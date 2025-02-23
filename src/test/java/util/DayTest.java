package util;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DayTest {
    @DisplayName("해당 날짜가 휴일인지 확인합니다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 25})
    void isHolidayTest(int value) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 25, 10, 0);
        Assertions.assertTrue(Day.isHoliday(value, today));
    }

    @DisplayName("미래 시점인지 확인합니다.")
    @Test
    void isFutureTest() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 2, 10, 0);
        int day = 3;
        Assertions.assertTrue(Day.isFuture(day, today));
    }

    @DisplayName("미래 시점이면 에러가 발생합니다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 9, 10})
    void isFutureErrorTest(int value) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 2, 10, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> Day.validateDay(value, today));
    }

    @DisplayName("휴일이면 에러가 발생합니다.")
    @ParameterizedTest
    @ValueSource(ints = {25, 14, 15})
    void isHolidayErrorTest(int value) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 2, 10, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> Day.validateDay(value, today));
    }
}