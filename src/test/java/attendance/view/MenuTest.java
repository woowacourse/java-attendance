package attendance.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    @DisplayName("잘못된 메뉴 선택의 입력인 경우 예외가 발생한다")
    void shouldThrowExceptionWhenInvalidMenuSelection() {
        // given
        String input = "X";

        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> Menu.find(input))
                .withMessage("[ERROR] 잘못된 메뉴 선택의 입력입니다.");
    }
}
