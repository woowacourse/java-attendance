package view;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class InputValidatorTest {

    @DisplayName("입력받은 값이 공백이나 null이 아니라면 예외를 던지지 않는다")
    @Test
    void correctInputTest() {
        assertThatCode(() -> new InputValidator().validateNullOrEmptyInput("hello"))
                .doesNotThrowAnyException();
    }

    @DisplayName("입력받은 값이 공백이나 null이라면 예외가 발생한다")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyOrNullInputTest(final String input) {
        assertThatThrownBy(() -> new InputValidator().validateNullOrEmptyInput(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 입력입니다.");
    }
}
