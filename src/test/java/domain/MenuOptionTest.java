package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static util.Constants.*;

import controller.MenuOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MenuOptionTest {
    @DisplayName("선택한 메뉴가 존재할 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q", "q"})
    void test5(String validSelectedMenu) {
        assertDoesNotThrow(
                () -> MenuOption.from(validSelectedMenu));
    }

    @DisplayName("선택한 메뉴가 존재하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0"})
    void test4(String invalidSelectedMenu) {
        assertThatThrownBy(
                () -> MenuOption.from(invalidSelectedMenu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
