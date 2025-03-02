package view;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {
    @DisplayName("공백이 입력될 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void test5(String validSelectedMenu) {
        assertThrowsIllegalArgumentException(
                () -> InputValidator.validateNotEmpty(validSelectedMenu));
    }

    @DisplayName("시간 형식이 올바를 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"09:59", "10:00", "08:00", "23:00"})
    void test6(String validTime) {
        assertDoesNotThrow(
                () -> InputValidator.validateTime(validTime));
    }


    @DisplayName("시간 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"9:59","a", "1", "10:3", "10;10"})
    void test1(String invalidTime) {
        assertThrowsIllegalArgumentException(
                () -> InputValidator.validateTime(invalidTime));
    }

    @DisplayName("날짜 형식이 올바를 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"2"})
    void test8(String validDay) {
        assertDoesNotThrow(
                () -> InputValidator.validateDay(validDay));
    }

    @DisplayName("날짜 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"32", "0"})
    void test3(String invalidDay) {
        assertThrowsIllegalArgumentException(
                () -> InputValidator.validateDay(invalidDay));
    }

    @DisplayName("오늘 이후의 날짜일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"15"})
    void test4(String invalidDay) {
        assertThrowsIllegalArgumentException(
                () -> InputValidator.validateDay(invalidDay));
    }

    void assertThrowsIllegalArgumentException(ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
