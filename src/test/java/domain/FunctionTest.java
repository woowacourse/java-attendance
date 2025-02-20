package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FunctionTest {
    @DisplayName("사용자 입력이 잘못되면 에러가 발생합니다.")
    @Test
    void functionInputErrorTest() {
        String testRawInput = "q";
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Function(testRawInput));
    }

    @DisplayName("올바른 사용자 입력을 테스트합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q"})
    void functionInputTest(String input) {
        Assertions.assertDoesNotThrow(() -> new Function(input));
    }

    @DisplayName("입력한 기능이 동일한지 확인합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q"})
    void functionEqualTest(String input) {
        Function function = new Function(input);
        Assertions.assertTrue(function.equals(input));
    }
}