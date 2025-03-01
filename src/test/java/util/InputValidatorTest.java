package util;

import static constant.ErrorMessage.INVALID_INPUT_NULL_OR_BLANK;
import static constant.ErrorMessage.INVALID_INTEGER_FORMAT;
import static constant.ErrorMessage.INVALID_TIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    @Test
    @DisplayName("null 입력 시 예외가 발생한다.")
    void test1() {
        // given
        String input = null;

        // when & then
        assertThatThrownBy(() -> InputValidator.validateNullOrBlank(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_NULL_OR_BLANK.getMessage());
    }

    @Test
    @DisplayName("공백 입력 시 예외가 발생한다.")
    void test2() {
        // given
        String input = " ";

        // when & then
        assertThatThrownBy(() -> InputValidator.validateNullOrBlank(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_NULL_OR_BLANK.getMessage());
    }

    @Test
    @DisplayName("정수 입력 시 예외가 발생하지 않는다.")
    void test3() {
        // given
        String input = "12";

        // when & then
        assertThatCode(() -> InputValidator.validateInteger(input))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("정수가 아닌 입력 시 예외가 발생한다.")
    void test4() {
        // given
        String input = "AB";

        // when & then
        assertThatThrownBy(() -> InputValidator.validateInteger(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INTEGER_FORMAT.getMessage());
    }

    @Test
    @DisplayName("시간 형식 입력 시 예외가 발생하지 않는다.")
    void test5() {
        // given
        String input = "09:59";

        // when & then
        assertThatCode(() -> InputValidator.validateTime(input))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("시간 형식이 아닌 입력 시 예외가 발생한다.")
    void test6() {
        // given
        String input = "09:60";

        // when & then
        assertThatThrownBy(() -> InputValidator.validateTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_TIME_FORMAT.getMessage());
    }

    @Test
    @DisplayName("시간 형식이 아닌 입력 시 예외가 발생한다.")
    void test7() {
        // given
        String input = "9:00";

        // when & then
        assertThatThrownBy(() -> InputValidator.validateTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_TIME_FORMAT.getMessage());
    }
}