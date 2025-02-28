package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandTest {
    @DisplayName("입력을 하면 그에 맞는 커맨드를 찾을 수 있다")
    @Test
    void findCommand() {
        assertThat(Command.findCommand("1")).isEqualTo(Command.ATTEND_TODAY);
    }
}
