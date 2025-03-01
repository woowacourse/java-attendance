package util;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DayOfWeekConvertorTest {

    @DisplayName("DayOfWeek 객체를 기반으로 한글 요일을 얻는 기능 테스트")
    @ParameterizedTest
    @MethodSource("provideDayOfWeekAndKorean")
    void parseDayOfWeekTest(DayOfWeek dayOfWeek, String expected) {
        Assertions.assertThat(DayOfWeekConvertor.convertToKorean(dayOfWeek))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideDayOfWeekAndKorean() {
        return Stream.of(
                Arguments.arguments(DayOfWeek.MONDAY, "월"),
                Arguments.arguments(DayOfWeek.TUESDAY, "화"),
                Arguments.arguments(DayOfWeek.WEDNESDAY, "수"),
                Arguments.arguments(DayOfWeek.THURSDAY, "목"),
                Arguments.arguments(DayOfWeek.FRIDAY, "금"),
                Arguments.arguments(DayOfWeek.SATURDAY, "토"),
                Arguments.arguments(DayOfWeek.SUNDAY, "일"));
    }
}
