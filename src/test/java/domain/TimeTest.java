package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeTest {
    @Test
    @DisplayName("시간과 오늘날짜를 받아 정확한 LocalDateTime을 반환합니다.")
    void getValidLocalDateTimeTest() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        String rawTime = "11:00";
        Time time = new Time(rawTime);

        Assertions.assertEquals(time.convertTime(), LocalTime.of(11, 0));
    }

    @ParameterizedTest
    @DisplayName("입력된 시간이 지정된 시간 사이에 있으면 참을 반환합니다.")
    @ValueSource(strings = {"08:00", "12:00", "23:00"})
    void betweenTimeTrueTest(String value) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        Time time = new Time(value);

        Assertions.assertTrue(time.isBetweenTime(startTime, endTime));
    }

    @ParameterizedTest
    @DisplayName("입력된 시간이 지정된 시간 사이에 없으면 거짓을 반환합니다.")
    @ValueSource(strings = {"00:00", "07:59", "23:01"})
    void betweenTimeFalseTest(String value) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        Time time = new Time(value);

        Assertions.assertFalse(time.isBetweenTime(startTime, endTime));
    }
}