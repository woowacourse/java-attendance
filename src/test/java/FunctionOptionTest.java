import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FunctionOptionTest {
    private static Stream<Arguments> testCasesForFindOptionTest() {
        return Stream.of(
                Arguments.of("1", FunctionOption.REGISTER_ATTENDANCE),
                Arguments.of("2", FunctionOption.UPDATE_ATTENDANCE),
                Arguments.of("3", FunctionOption.CHECK_ATTENDANCE_HISTORY_OF_CREW),
                Arguments.of("4", FunctionOption.CHECK_EXPULSION_CANDIDATES),
                Arguments.of("Q", FunctionOption.QUIT)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForFindOptionTest")
    @DisplayName("기능 옵션 값에 따라 기능 옵션을 반환한다")
    void test(String input, FunctionOption option) {
        // when
        FunctionOption actual = FunctionOption.findBySign(input);

        // then
        assertEquals(option, actual);
    }

    @Test
    @DisplayName("존재하지 않는 기능 옵션에 대해 예외를 던진다")
    void test2() {
        // given
        String invalidSign = "invalidSign";

        // when & then
        assertThatThrownBy(() -> FunctionOption.findBySign(invalidSign))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
