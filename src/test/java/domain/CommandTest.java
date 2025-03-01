package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CommandTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4"})
    void 등록된_커맨드인지_확인한다(String inputOption) {

        assertDoesNotThrow(() -> Command.identify(inputOption));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "5", "6", "7"})
    void 등록되지_않은_커맨드의_경우_예외를_발생시킨다(String inputOption) {

        assertThatThrownBy(() -> Command.identify(inputOption))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 커맨드는 존재하지 않습니다.");
    }
}
