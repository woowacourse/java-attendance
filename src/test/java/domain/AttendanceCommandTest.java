package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceCommandTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("입력 커멘드로 출석 기능을 찾는다.")
    void test1(final String command, final AttendanceCommand expected) {
        //should
        assertThat(AttendanceCommand.findByCommand(command)).isEqualTo(expected);

    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of("1", AttendanceCommand.CHECK),
                Arguments.of("2", AttendanceCommand.MODIFY),
                Arguments.of("3", AttendanceCommand.LOOK_UP),
                Arguments.of("4", AttendanceCommand.PENALTY_CHECK),
                Arguments.of("Q", AttendanceCommand.QUIT)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"6", "q", "!", "@", "", " "})
    @DisplayName("유효하지 않은 커멘드 이므로 예외가 발생한다.")
    void test2(final String command) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> AttendanceCommand.findByCommand(command));

    }
}
