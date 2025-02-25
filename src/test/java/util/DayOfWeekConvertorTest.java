package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DayOfWeekConvertorTest {

    @ParameterizedTest
    @MethodSource("generateDayOfWeek")
    void 요일_변환_테스트(DayOfWeek dayOfWeek, String expected) {
        assertThat(DayOfWeekConvertor.convertDayOfWeekToKorean(dayOfWeek)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateDayOfWeek() {
        return Stream.of(
                Arguments.arguments(DayOfWeek.MONDAY, "월"),
                Arguments.arguments(DayOfWeek.TUESDAY, "화"),
                Arguments.arguments(DayOfWeek.WEDNESDAY, "수"),
                Arguments.arguments(DayOfWeek.THURSDAY, "목"),
                Arguments.arguments(DayOfWeek.FRIDAY, "금"),
                Arguments.arguments(DayOfWeek.SATURDAY, "토"),
                Arguments.arguments(DayOfWeek.SUNDAY, "일")
        );
    }
}
