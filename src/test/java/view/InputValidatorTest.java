package view;

import static org.assertj.core.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {

    @DisplayName("시간 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"9:59", "", " ", "a", "1", "10:3", "10;10"})
    void test1(String invalidTimeFormat) {
        assertThatThrownBy(() -> InputValidator.validateTime(invalidTimeFormat))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("닉네임이 1자 이하, 5자 이상일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"미", "미미미미미", "", " "})
    void test2(String invalidNameFormat) {
        assertThatThrownBy(() -> InputValidator.validateName(invalidNameFormat))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
