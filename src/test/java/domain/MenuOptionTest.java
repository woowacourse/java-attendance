package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MenuOptionTest {

    @ParameterizedTest
    @MethodSource("generateOption")
    void 메뉴_변환(String input, MenuOption expected) {
        Assertions.assertThat(MenuOption.getMenuOption(input))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> generateOption() {
        return Stream.of(
                Arguments.arguments("1", MenuOption.ATTENDANCE_CHECK),
                Arguments.arguments("2", MenuOption.ATTENDANCE_CORRECTION),
                Arguments.arguments("3", MenuOption.CREW_ATTENDANCE_CHECK),
                Arguments.arguments("4", MenuOption.CHECK_EXPELLED_CREW),
                Arguments.arguments("Q", MenuOption.QUIT)
        );
    }

    @Test
    void 존재하지_않는_메뉴_입력() {
        assertThatThrownBy(() -> MenuOption.getMenuOption("1번"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
