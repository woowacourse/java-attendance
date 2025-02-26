package View;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import view.UserCommandType;

class UserCommandTypeTest {
    @DisplayName("잘못된 사용자 입력일 시, 에러가 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"q", "0", "6", "ㅋㅋ"})
    public void validateUserCommandErrorTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> UserCommandType.validateUserCommand(value));
    }

    @DisplayName("올바른 사용자 입력일 시, 에러가 나오지 않습니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q"})
    public void validateUserCommandTest(String value) {
        Assertions.assertDoesNotThrow(
                () -> UserCommandType.validateUserCommand(value));
    }
}