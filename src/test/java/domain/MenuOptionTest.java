package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MenuOptionTest {

    @Test
    void 메뉴_변환() {
        Assertions.assertThat(MenuOption.getMenuOption("1"))
                .isEqualTo(MenuOption.ATTENDANCE_CHECK);
    }

    @Test
    void 존재하지_않는_메뉴_입력() {
        assertThatThrownBy(() -> MenuOption.getMenuOption("1번"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
