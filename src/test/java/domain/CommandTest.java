package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CommandTest {

    @DisplayName("1, 2, 3, 4, Q중에 하나를 입력받을 시 해당되는 enum을 반환")
    @ParameterizedTest
    @MethodSource("methodSources")
    void validCommand(final String commandNumber, final Command expectedCommand) {
        // given
        // when
        final Command command = Command.findByCommandNumber(commandNumber);

        // then
        assertThat(command).isEqualTo(expectedCommand);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments("1", Command.CHECK),
                Arguments.arguments("2", Command.EDIT),
                Arguments.arguments("3", Command.VIEW_ATTENDANCE_BY_CREW),
                Arguments.arguments("4", Command.VIEW_AT_RISK_MEMBERS),
                Arguments.arguments("Q", Command.QUIT)
        );
    }

    @DisplayName("올바르지 않은 명령어 입력시 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"6", "hi", "-2", "+1"})
    void invalidCommand(final String commandNumber) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Command.findByCommandNumber(commandNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
