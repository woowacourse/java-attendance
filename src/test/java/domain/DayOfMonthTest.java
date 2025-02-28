package domain;

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
}