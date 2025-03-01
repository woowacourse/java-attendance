package util;

import static constant.ErrorMessage.INVALID_INPUT_NULL_OR_BLANK;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    @Test
    @DisplayName("입력 값은 null일 수 없다.")
    void test1() {
        // given
        String input = null;

        // when & then
        assertThatThrownBy(() -> InputValidator.validateNullOrBlank(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_NULL_OR_BLANK.getMessage());
    }

    @Test
    @DisplayName("입력 값은 공백일 수 없다.")
    void test2() {
        // given
        String input = " ";

        // when & then
        assertThatThrownBy(() -> InputValidator.validateNullOrBlank(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_NULL_OR_BLANK.getMessage());
    }
}