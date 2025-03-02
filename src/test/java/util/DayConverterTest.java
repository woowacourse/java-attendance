package util;

import domain.DayOfMonth;
import domain.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DayConverterTest {

    @ParameterizedTest
    @DisplayName("날짜를 받으면 한글 요일을 반환합니다.")
    @CsvSource(value = {"월요일:2", "화요일:3", "수요일:4", "목요일:5", "금요일:6", "토요일:7", "일요일:8"}, delimiter = ':')
    void getKoreanDayOfWeekTest(String dayOfWeek, int value) {
        LocalDate today = LocalDate.of(2024, 12, value);

        Assertions.assertEquals(dayOfWeek, DayConverter.getKoreanDayOfWeek(today));
    }

    @Test
    @DisplayName("시간을 받아 오늘 날짜와 결합합니다.")
    void combineTimeAndDateTest() {
        Time time = new Time("12:00");
        LocalDate today = LocalDate.of(2024, 12, 2);

        Assertions.assertEquals(LocalDateTime.of(2024, 12, 2, 12, 0), DayConverter.combineTimeAndDate(time, today));
    }

    @ParameterizedTest
    @DisplayName("오늘 날짜를 특정 날짜로 변경 가능한지 확인합니다.")
    @ValueSource(ints = {1, 7, 8, 14, 15, 21, 22, 28, 25, 29})
    void combineDayAndDateTest(int value) {
        DayOfMonth dayOfMonth = new DayOfMonth(value);
        LocalDate today = LocalDate.of(2024, 12, 2);
        Assertions.assertEquals(LocalDate.of(2024, 12, value), DayConverter.combineDayAndDate(dayOfMonth, today));
    }
}