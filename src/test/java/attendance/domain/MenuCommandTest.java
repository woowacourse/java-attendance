package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MenuCommandTest {
    @DisplayName("예외: 잘못된 메뉴 명령어 입력에 대한 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"일", "5", "X"})
    public void causeException(String menuCommand) {
        assertThatThrownBy(() -> {
            MenuCommand.of(menuCommand);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 메뉴 명령어 입력에 대한 정상 처리")
    @ParameterizedTest
    @CsvSource(value = {"1, CHECK", "2, MODIFY", "3, LOOKUP", "4, EXPEL", "Q, QUIT"}, delimiter = ',')
    public void successExecution(String commandInput, String menuCommand) {
        assertThat(MenuCommand.of(commandInput)).isEqualTo(MenuCommand.valueOf(menuCommand));
    }
}
