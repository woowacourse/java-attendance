package domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import exception.ErrorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MenuTest {

    @ParameterizedTest
    @CsvSource({"1,ATTENDANCE_REGISTER", "2,ATTENDANCE_EDIT", "3,CREW_ATTENDANCE", "4,EXPULSION_RISK", "Q,QUIT"})
    @DisplayName("메뉴 선택 기능 테스트")
    void 메뉴_선택_기능_테스트(String code, Menu menu) {
        // given
        Menu selectedMenu = Menu.of(code);
        // when & then
        assertEquals(menu, selectedMenu);
    }

    @Test
    @DisplayName("메뉴 선택 예외 테스트")
    void 메뉴_선택_예외_테스트() {
        // given
        String code = "5";
        // when & then
        assertThatThrownBy(() -> Menu.of(code))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }
}
