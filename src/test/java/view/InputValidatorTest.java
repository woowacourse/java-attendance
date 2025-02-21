package view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {
    InputValidator inputValidator = new InputValidator();

    @DisplayName("시간 형식이 잘못되면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"aa:bb", "123:12", "abcd", " ", "", "-1:-10"})
    void inputTimeErrorTest(String input) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> inputValidator.validateTimeFormat(input));
    }

    @DisplayName("시간 형식이 nn:nn 인지 검사한다")
    @ParameterizedTest
    @ValueSource(strings = {"09:20", "1:10", "30:99"})
    void inputTimeTest(String input) {
        Assertions.assertDoesNotThrow(() -> inputValidator.validateTimeFormat(input));
    }

    @DisplayName("날짜 형식이 잘못되면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"123", "ab", "-10", " ", ""})
    void dayTimeErrorTest(String input) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> inputValidator.validateDayFormat(input));
    }

    @DisplayName("날짜 형식이 nn인지 검사한다")
    @ParameterizedTest
    @ValueSource(strings = {"10", "99", "1"})
    void dayTimeTest(String input) {
        Assertions.assertDoesNotThrow(() -> inputValidator.validateDayFormat(input));
    }
}