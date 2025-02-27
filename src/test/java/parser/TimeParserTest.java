package parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import view.parser.TimeParser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimeParserTest {

    @ParameterizedTest
    @DisplayName("시의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"-1", "24", "a"})
    void validateHourTest(String hour){
        assertThatThrownBy(() -> TimeParser.validateHour(hour))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("분의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"60", "-1", "a"})
    void validateMinuteTest(String minute) {
        assertThatThrownBy(() -> TimeParser.validateMinute(minute))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("시간 입력 형식이 다르면 예외가 발생한다")
    @ValueSource(strings = {"12/34", "12:12:12"})
    void validateTimeFormatTest(String time) {
        assertThatThrownBy(() -> TimeParser.validateTimeFormat(time))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
