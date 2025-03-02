package util.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DayOfWeekConverterTest {

    @ParameterizedTest
    @CsvSource({"SUNDAY,일", "MONDAY,월", "TUESDAY,화", "WEDNESDAY,수", "THURSDAY,목", "FRIDAY,금", "SATURDAY,토"})
    @DisplayName("요일 한국어 변환 기능 테스트")
    void 요일_한국어_변환_기능_테스트(String dayOfWeek, String dayOfWeekToKorean) {
        // given & when & then
        assertEquals(dayOfWeekToKorean, DayOfWeekConverter.convertDayOfWeekToKorean(DayOfWeek.valueOf(dayOfWeek)));
    }
}
