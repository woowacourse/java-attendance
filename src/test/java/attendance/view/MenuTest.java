package attendance.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    @DisplayName("입력과 일치한 메뉴를 반환한다")
    void 입력과_일치한_메뉴를_반환한다() {
        // given
        String input = "1";

        // when
        Menu result = Menu.find(input);

        // then
        Assertions.assertThat(result).isEqualTo(Menu.CHECK);
    }

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
