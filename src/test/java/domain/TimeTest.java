package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeTest {
    @Test
    @DisplayName("시간과 오늘날짜를 받아 정확한 LocalDateTime을 반환합니다.")
    void getValidLocalDateTimeTest() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        String rawTime = "11:00";
        Time time = new Time(rawTime);

        Assertions.assertEquals(time.convertTime(), LocalTime.of(11, 0));
    }
}