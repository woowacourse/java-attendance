package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MenuCommandTest {
    @DisplayName("예외: 올바르지 않은 메뉴 명령어 입력에 대한 처리")
    @ValueSource(strings = {"5", "일", "q"})
    @ParameterizedTest
    void checkMenuCommand(String input) {
        assertThatThrownBy(() -> MenuCommand.toCommand(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
