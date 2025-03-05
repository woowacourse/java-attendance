package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputParserTest {

    @DisplayName("시간 문자열을 LocalTime으로 변환한다")
    @Test
    void timeParseTest() {
        assertThat(InputParser.parseTime("11:50")).hasHour(11).hasMinute(50);
    }

    @DisplayName("시간 문자열의 포맷이 올바르지 않으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"11시50분", "11-50", "25:00", "10:60"})
    void wrongTimeFormatParseTest(final String input) {
        assertThatThrownBy(() -> InputParser.parseTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 시간 입력 형식입니다.");
    }

    @DisplayName("날짜(일)을 2024년 12월의 LocalDate로 변환한다")
    @Test
    void dayParseTest() {
        assertThat(InputParser.parseDayOfMonth("5"))
                .hasYear(2024)
                .hasMonth(Month.DECEMBER)
                .hasDayOfMonth(5);
    }

    @DisplayName("날짜 문자열의 포맷이 올바르지 않으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"5일", "0", "32"})
    void wrongDayFormatParseTest(final String input) {
        assertThatThrownBy(() -> InputParser.parseDayOfMonth(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 날짜(일) 입력 형식입니다.");
    }

    @DisplayName("yyyy-MM-dd 형식의 문자열을 LocalDate로 변환한다")
    @Test
    void csvDateParseTest() {
        assertThat(InputParser.parseCsvDate("2024-12-13"))
                .hasYear(2024)
                .hasMonth(Month.DECEMBER)
                .hasDayOfMonth(13);
    }

    @DisplayName("yyyy-MM-dd 형식이 올바르지 않으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"2024/12/13", "2024년 12월 13일"})
    void wrongCsvDateFormatParseTest(final String input) {
        assertThatThrownBy(() -> InputParser.parseCsvDate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 날짜(일) 입력 형식입니다.");
    }
}
