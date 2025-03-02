package attendance.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandStatusTest {

    @ParameterizedTest
    @MethodSource
    void 입력값에_따라_커맨드를_조회한다(final String status, CommandStatus expected) {
        // Given

        // When & Then
        assertThat(CommandStatus.from(status)).isEqualTo(expected);
    }

    private static Stream<Arguments> 입력값에_따라_커맨드를_조회한다() {
        return Stream.of(
                Arguments.of("1", CommandStatus.ATTEND),
                Arguments.of("2", CommandStatus.MODIFY),
                Arguments.of("3", CommandStatus.INQUIRY_CREW),
                Arguments.of("4", CommandStatus.FIND_DISMISSAL),
                Arguments.of("Q", CommandStatus.QUIT)
        );
    }

    @Test
    void 존재하지_않은_기능을_입력하면_예외가_발생한다() {
        // Given

        // When & Then
        assertThatThrownBy(() -> CommandStatus.from("AA"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하는 기능이 아닙니다.");
    }
}
