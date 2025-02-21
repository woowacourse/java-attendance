package view;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {
    @DisplayName("기능 선택을 위해 [1, 2, 3, 4, Q(q)]의 문자를 입력할 시 정상 동작한다")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q", "q"})
    void test1(String menuInput) {
        assertDoesNotThrow(() -> InputValidator.validateMenuInput(menuInput));;
    }

    @DisplayName("기능 선택을 위해 [1, 2, 3, 4, Q(q)]의 문자를 입력할 시 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"5", "a", "2200000000"})
    void test2(String menuInput) {
        assertThatThrownBy(() -> InputValidator.validateMenuInput(menuInput))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
