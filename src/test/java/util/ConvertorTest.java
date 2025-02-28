package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ConvertorTest {

    @ParameterizedTest
    @CsvSource({"SUNDAY,일", "MONDAY,월", "TUESDAY,화", "WEDNESDAY,수", "THURSDAY,목", "FRIDAY,금", "SATURDAY,토"})
    @DisplayName("요일 한국어 변환 기능 테스트")
    void 요일_한국어_변환_기능_테스트(String dayOfWeek, String dayOfWeekToKorean) {
        // given & when & then
        assertEquals(dayOfWeekToKorean, Convertor.convertDayOfWeekToKorean(DayOfWeek.valueOf(dayOfWeek)));
    }

    @Test
    @DisplayName("문자열 날짜 및 시간 변환 기능 테스트")
    void 문자열_날짜_및_시간_변환_기능_테스트() {
        // given
        String dateTimeStr = "2024-12-20 10:05";
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 20, 10, 5);
        // when
        LocalDateTime convertedDateTime = Convertor.convertStringToDateTime(dateTimeStr);
        // then
        assertEquals(dateTime, convertedDateTime);
    }
}
