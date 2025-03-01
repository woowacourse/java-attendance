package controller;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MenuOptionTest {

    @DisplayName("입력 -> 옵션 파싱 테스트")
    @ParameterizedTest
    @MethodSource("provideInputAndOption")
    void parseInputToOptionTest(String input, MenuOption expected) {
        Assertions.assertThat(MenuOption.findOptionByCommand(input))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideInputAndOption() {
        return Stream.of(
                Arguments.arguments("1", MenuOption.REGISTER_ATTENDANCE),
                Arguments.arguments("2", MenuOption.EDIT_ATTENDANCE),
                Arguments.arguments("3", MenuOption.SHOW_CREW_ATTENDANCE),
                Arguments.arguments("4", MenuOption.SHOW_EXPELLED_CREWS),
                Arguments.arguments("Q", MenuOption.QUIT)
        );
    }

    @DisplayName("존재하지 않는 옵션 입력 시 예외 발생 테스트")
    @Test
    void optionNotExistTest() {
        String input = "1번";

        Assertions.assertThatThrownBy(() -> MenuOption.findOptionByCommand(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
