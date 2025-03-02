package parser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import view.parser.DayParser;

public class DayParserTest {
    @ParameterizedTest
    @DisplayName("날짜의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"-1", "32", "a"})
    void validateHourTest(String day) {
        assertThatThrownBy(() -> DayParser.validateDayFormat(day))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
