package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuOptionTest {
    @DisplayName("올바른 메뉴를 입력한다")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q"})
    void menuOptionTest(String input) {
        Assertions.assertDoesNotThrow(() -> MenuOption.selectOption(input));
    }

    @DisplayName("특정 메뉴 키가 아니라면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "abc", "q", "123"})
    void menuOptionTestException(String input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> MenuOption.selectOption(input));
    }
}