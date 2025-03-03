import domain.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandTest {
    @DisplayName("입력을 하면 그에 맞는 커맨드를 찾을 수 있다")
    @Test
    void findCommand() {
        Assertions.assertThat(Command.findCommand("1")).isEqualTo(Command.ATTEND_TODAY);
    }
}
