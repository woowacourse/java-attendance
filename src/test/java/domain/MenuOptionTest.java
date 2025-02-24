package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuOptionTest {
    @DisplayName("사용자 입력이 잘못되면 에러가 발생합니다.")
    @Test
    void functionInputErrorTest() {
        String testRawInput = "q";
        Assertions.assertThrows(IllegalArgumentException.class, () -> MenuOption.getMenuOption(testRawInput));
    }

    @DisplayName("올바른 사용자 입력을 테스트합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q"})
    void functionInputTest(String input) {
        Assertions.assertDoesNotThrow(() -> MenuOption.getMenuOption(input));
    }

    @DisplayName("입력한 기능이 동일한지 확인합니다.")
    @Test
    void functionEqualTest() {
        MenuOption menuOption = MenuOption.getMenuOption("1");
        Assertions.assertEquals(menuOption, MenuOption.CHECK_ATTENDANCE);
    }

    @DisplayName("Q 입력 시 true를 반환합니다.")
    @Test
    void functionTrueTest() {
        Assertions.assertTrue(MenuOption.isExit("Q"));
    }

    @DisplayName("Q가 아닌 경우 입력 시 false를 반환합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"q", "1", "2", "3", "4"})
    void functionFalseTest() {
        Assertions.assertFalse(MenuOption.isExit("Q"));
    }
}