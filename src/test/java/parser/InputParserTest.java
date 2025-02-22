package parser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputParserTest {

    @Nested
    @DisplayName("시간 입력 파싱 테스트")
    class TimeParserTest {
        @Test
        @DisplayName("구분자가 다르면 예외가 발생한다.")
        void delimiterTest() {
            assertThatThrownBy(() -> InputParser.timeParser("12/34"))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @ParameterizedTest
        @DisplayName("시간 형식이 다르면 예외가 발생한다.")
        @ValueSource(strings = {"35:31", "11:70", "a:b"})
        void validateTimeFormatTest(String time) {
            assertThatThrownBy(() -> InputParser.timeParser(time))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("날짜 입력 파싱 테스트")
    class DayParserTest {
        @ParameterizedTest
        @DisplayName("날짜 형식이 다르면 예외가 발생한다.")
        @ValueSource(strings = {"32", "0", "a"})
        void validateDayTest(String day) {
            assertThatThrownBy(() -> InputParser.dayParser(day))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
