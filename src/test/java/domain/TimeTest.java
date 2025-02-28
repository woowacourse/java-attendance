package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeTest {

    @ParameterizedTest
    @DisplayName("시간 포맷이 잘못되면 에러가 발생합니다.")
    @ValueSource(strings = {"dd", "111:11", "1:12", "11:111", "1:", " "})
    void InvalidTimeFormatTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Time(value));
    }

    @ParameterizedTest
    @DisplayName("시간이 잘못되면 에러가 발생합니다.")
    @ValueSource(strings = {"24:00", "01:60", "13:-01"})
    void InvalidTimeTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Time(value));
    }

    @Test
    @DisplayName("시간과 오늘날짜를 받아 정확한 LocalDateTime을 반환합니다.")
    void getValidLocalDateTimeTest() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        String rawTime = "11:00";
        Time time = new Time(rawTime);

        Assertions.assertEquals(time.convertTime(), LocalTime.of(11, 0));
    }
}