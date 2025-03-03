package view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {
    InputValidator inputValidator = new InputValidator();

    @DisplayName("시간형식이 맞는지 검사한다")
    @Test
    void timeFormatTest() {
        String time = "12:30";
        Assertions.assertDoesNotThrow(() -> inputValidator.validateTimeFormat(time));
    }

    @DisplayName("시간 형식이 HH:mm이 아니라면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {"111:11", "11:111", "ab:ad", " ", "jhknb"})
    void timeFormatTestException(String time) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> inputValidator.validateTimeFormat(time));
    }

    @DisplayName("날짜 형식을 검증한다")
    @Test
    void dateFormatTest() {
        String date = "12";
        Assertions.assertDoesNotThrow(() -> inputValidator.validateDateFormat(date));
    }

    @DisplayName("날짜 형식이 DD가 아니라면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {"111", "-100", "abcd", " "})
    void dateFormatTestException(String date) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> inputValidator.validateDateFormat(date));
    }
}