package attendance.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MenuTest {

    @Test
    void 메뉴에_없는_입력이_들어오면_예외() {
        assertThatThrownBy(() -> Menu.from("5")).isInstanceOf(IllegalArgumentException.class);
    }
}