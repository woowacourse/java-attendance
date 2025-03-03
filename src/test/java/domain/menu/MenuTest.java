package domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
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

    @ParameterizedTest
    @CsvSource({
            "ATTENDANCE_REGISTER,1,출석 확인",
            "ATTENDANCE_EDIT,2,출석 수정",
            "CREW_ATTENDANCE,3,크루별 출석 기록 확인",
            "EXPULSION_RISK,4,제적 위험자 확인",
            "QUIT,Q,종료"
    })
    @DisplayName("메뉴 선택 설명 기능 테스트")
    void 메뉴_선택_설명_기능_테스트(Menu menu, String code, String description) {
        // given & when & then
        assertSoftly(softly -> {
            assertEquals(code, menu.getCode());
            assertEquals(description, menu.getDescription());
        });
    }
}
