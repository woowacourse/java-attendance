package util;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOfWeekConverterTest {
    @DisplayName("날짜를 입력하면 한글 요일을 반환합니다.")
    @Test
    void convertDayOfWeekTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 0, 0);
        Assertions.assertEquals("월요일", DayOfWeekConverter.convertDayOfWeek(localDateTime));
    }

    @DisplayName("수정할 날짜와 현재 시간을 입력하면 한글 요일을 반환합니다.")
    @Test
    void convertChangeDayOfWeekTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 0, 0);
        int changeDay = 3;

        Assertions.assertEquals("화요일", DayOfWeekConverter.convertDayOfWeek(changeDay, localDateTime));
    }
}