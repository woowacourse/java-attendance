package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DayTypeTest {
    @DisplayName("평일인 경우 평일을 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6})
    void weekdayTest(int day) {
        Assertions.assertEquals(DayType.WEEKDAY, DayType.calculateDayType(day));
    }

    @DisplayName("주말인 경우 주말을 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 14, 15})
    void weekendTest(int day) {
        Assertions.assertEquals(DayType.WEEKEND, DayType.calculateDayType(day));
    }

    @DisplayName("공휴일인 경우 공휴일을 반환한다")
    @Test
    void holidayTest() {
        Assertions.assertEquals(DayType.HOLIDAY, DayType.calculateDayType(25));
    }
}