package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    @DisplayName("심볼의 입력값으로 명령어 객체를 생성할 수 있는 지")
    void findBySymbolSuccess() {

        // given
        final String symbolInput = "1";
        final Command expected = Command.ATTENDANCE_CHECK;

        // when
        final Command command = Command.findBySymbol(symbolInput);

        // then
        Assertions.assertThat(command).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않는 심볼을 입력했을 때 예외 처리가 되어있는 지")
    void findBySymbolFailure() {

        // given
        final String symbolInput = "5";

        // when
        Assertions.assertThatThrownBy(
                () -> Command.findBySymbol(symbolInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}