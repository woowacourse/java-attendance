package attendance.controller;

import static attendance.error.ErrorMessage.NO_MAIN_OPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainOptionTest {

    @DisplayName("입력한 번호대로 옵션이 생성된다")
    @Test
    void test_MainOption() {
        // given
        String command = "1";

        // when
        MainOption option = MainOption.from(command);

        // then
        assertThat(option).isEqualTo(MainOption.CHECK_ATTENDANCE);
    }

    @DisplayName("옵션에 없는 번호 입력 시 예외가 발생한다")
    @Test
    void test_MainOption_error() {
        // given
        String command = "0";

        // when
        assertThatThrownBy(() -> MainOption.from(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_MAIN_OPTION);
    }

}