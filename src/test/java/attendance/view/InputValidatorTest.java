package attendance.view;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.view.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidator();
    }

    @DisplayName("입력이 null이나 공백이 아니라면 예외가 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"호떡", "밍트"})
    void validateInputTest(final String input) {
        assertThatCode(() -> inputValidator.validateIsNullOrEmpty(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("입력이 null이거나 공백이라면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void nullOrEmptyTest(final String input) {
        assertThatThrownBy(() -> inputValidator.validateIsNullOrEmpty(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 잘못된 입력입니다.");
    }
}
