package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTest {

    @Test
    @DisplayName("기능 목록에 있는 입력을 하면 성공한다.")
    void commandTest1() {
        assertThatCode(() -> Command.of("2")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기능 목록에 없는 입력을 하면 예외를 던진다")
    void commandTest2() {
        assertThatThrownBy(() -> Command.of("6")).isInstanceOf(IllegalArgumentException.class);
    }
}