package view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    @ParameterizedTest
    @DisplayName("시간 포맷이 잘못되면 예외가 발생합니다.")
    @ValueSource(strings = {"11,00", "133:00", ":11", "12:111", "11:", "1:11"})
    void invalidTimeFormatTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> InputValidator.validateTimeFormat(value));
    }

    @ParameterizedTest
    @DisplayName("시간이나 분이 잘못되면 예외가 발생합니다.")
    @ValueSource(strings = {"11:60", "24:00", "25:00"})
    void invalidTimeTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> InputValidator.validateTime(value));
    }

    @ParameterizedTest
    @DisplayName("입력이 정수가 아니면 예외가 발생합니다.")
    @ValueSource(strings = {"", " ", "ㄱ", "g"})
    void invalidIntegerTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> InputValidator.validateInteger(value));
    }

}