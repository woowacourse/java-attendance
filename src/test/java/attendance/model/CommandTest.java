package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("명령어 테스트")
class CommandTest {

    @DisplayName("명령어의 코드와 일치하는 문자열을 명령어로 바꿀 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "1, ATTENDANCE",
            "2, EDIT_ATTENDANCE",
            "3, ATTENDANCE_LOGS",
            "4, WARNING_LIST",
            "Q, QUIT"
    })
    void inputToCommandTest(String input, Command expected) {
        // when
        Command command = Command.from(input);

        // then
        assertThat(command)
                .isSameAs(expected);
    }

    @DisplayName("문자열로 명령어를 찾을 수 없는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "ATTENDANCE, 출석 확인",
            "EDIT_ATTENDANCE, 출석 수정",
            "ATTENDANCE_LOGS, 크루별 출석 기록 확인",
            "WARNING_LIST, 제적 위험자 확인",
            "QUIT, 종료"
    })
    void commandDescriptionTest(Command command, String expectedDescription) {
        // when & then
        assertThat(command.getDescription())
                .isEqualTo(expectedDescription);
    }

    @DisplayName("문자열로 명령어를 찾을 수 없는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "벨로",
            "QUIT",
            "ATTENDANCE",
            "EDIT_ATTENDANCE"
    })
    void shouldThrowException_WhenNotFoundCommand(String invalidInput) {
        // when & then
        assertThatCode(() -> Command.from(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 커맨드입니다. 입력: %s".formatted(invalidInput));
    }

    @DisplayName("명령어의 코드를 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "ATTENDANCE, 1",
            "EDIT_ATTENDANCE, 2",
            "ATTENDANCE_LOGS, 3",
            "WARNING_LIST, 4",
            "QUIT, Q"
    })
    void getCodeTest(Command command, String expectedCode) {
        // when & then
        assertThat(command.getCode())
                .isEqualTo(expectedCode);
    }

    @DisplayName("모든 명령어를 가져올 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "ATTENDANCE",
            "EDIT_ATTENDANCE",
            "ATTENDANCE_LOGS",
            "WARNING_LIST",
            "QUIT"
    })
    void getCommandsTest(Command command) {
        // when & then
        assertThat(Command.getCommands())
                .contains(command);
    }
}
