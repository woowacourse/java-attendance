package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeTest {
    @DisplayName("시간 문자열을 시간으로 변환한다")
    @Test
    void convertTimeTest() {
        String time = "22:00";
        Assertions.assertDoesNotThrow(() -> new Time(time));
    }

    @DisplayName("시간이 08:00 ~ 23:00이 아니면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {"07:59", "23:01"})
    void timeOnCampusTest(String time) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Time(time));
    }
}