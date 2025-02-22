package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class MenuOptionTest {

    @Test
    void 존재하지_않는_메뉴_입력() {
        assertThatThrownBy(() -> MenuOption.validateCommandExist("1번"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
