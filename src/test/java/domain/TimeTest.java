package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeTest {
    @ParameterizedTest
    @ValueSource(strings = {"23:00", "07:59", "00:00", "25:00", "09:70"})
    void validateTimeErrorTest(String time) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Time(time));
    }

    @ParameterizedTest
    @ValueSource(strings = {"08:00", "22:59", "13:00"})
    void validateTimeTest(String time) {
        Assertions.assertDoesNotThrow(() -> new Time(time));
    }
}