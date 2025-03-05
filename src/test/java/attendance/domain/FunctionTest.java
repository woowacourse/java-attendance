package attendance.domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FunctionTest {

    @ParameterizedTest
    @MethodSource("functionAndResult")
    void 기능_문자열_입력_시_올바른_기능_값을_반환한다(final String input, final Function expectedResult) {

        // given

        // when
        final Function result = Function.getFunction(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void 기능에_없는_입력_시_예외를_발생한다() {

        // given

        // when & then
        Assertions.assertThatThrownBy(() -> Function.getFunction("notValid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바른 기능을 입력해 주세요.");
    }

    public static Stream<Arguments> functionAndResult() {

        return Stream.of(
                Arguments.of("1", Function.ADD_ATTENDANCE),
                Arguments.of("2", Function.MODIFY_ATTENDANCE),
                Arguments.of("3", Function.GET_CREW_ATTENDANCES),
                Arguments.of("4", Function.GET_EXPULSION_CANDIDATES),
                Arguments.of("Q", Function.QUIT)
        );
    }
}
