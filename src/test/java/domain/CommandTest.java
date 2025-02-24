package domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandTest {

    @ParameterizedTest
    @MethodSource("methodSources")
    void 올바른_명령_문자를_입력시_명령_반환(String commandName, Command expectedCommand) {
        // given

        Command command = Command.findByCommandNumber(commandName);
        Assertions.assertThat(command).isEqualTo(expectedCommand);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments("1", Command.CHECK_ATTENDEES),
                Arguments.arguments("2", Command.EDIT_ATTENDANCE),
                Arguments.arguments("3", Command.CHECK_THE_ATTENDANCE_RECORD_BY_CREW),
                Arguments.arguments("4", Command.CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION),
                Arguments.arguments("Q", Command.QUIT)
        );
    }
}
