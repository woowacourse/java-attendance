package util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOfWeekConverterTest {
    @DisplayName("날짜를 입력하면 한글 요일을 반환합니다.")
    @Test
    void convertDayOfWeekTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,2,0,0);
        Assertions.assertEquals("월요일", DayOfWeekConverter.convertDayOfWeek(localDateTime));
    }
}