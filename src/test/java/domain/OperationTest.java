package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OperationTest {


    @Nested
    @DisplayName("성공 테스트")
    class SuccessTest {
        @ParameterizedTest
        @MethodSource("오퍼레이션")
        @DisplayName("사용자가 입력한 값으로 Operation을 찾는다.")
        void findOperationByInputValueTest(final String inputValue, final Operation operation) {
            //should
            assertThat(Operation.of(inputValue)).isEqualTo(operation);
        }

        private static Stream<Arguments> 오퍼레이션() {
            return Stream.of(
                    Arguments.of("1", Operation.ADD_ATTENDANCE),
                    Arguments.of("2", Operation.UPDATE_ATTENDANCE),
                    Arguments.of("3", Operation.LOOKUP_CREW_ATTENDANCE),
                    Arguments.of("4", Operation.LOOKUP_EXPULSION_CREWS),
                    Arguments.of("Q", Operation.QUIT)
            );
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailTest {
        @ParameterizedTest
        @ValueSource(strings = {"q", "d", "5"})
        @DisplayName("사용자가 입력한 값으로 Operation을 찾는다.")
        void findOperationByInputValueTest(final String inputValue) {
            //should
            assertThatIllegalArgumentException().isThrownBy(() -> Operation.of(inputValue));
        }
    }
}
