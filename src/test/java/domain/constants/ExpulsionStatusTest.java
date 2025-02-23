package domain.constants;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ExpulsionStatusTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("결석 수를 기준으로 올바른 제적 상태를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideExpulsionStatus")
        public void of(final int absenceCount, final ExpulsionStatus expected) throws Exception {
            // given & when
            final ExpulsionStatus actual = ExpulsionStatus.of(absenceCount);

            // then
            assertThat(actual).isSameAs(expected);
        }

        private static Stream<Arguments> provideExpulsionStatus() {
            return Stream.of(
                    Arguments.of(0, ExpulsionStatus.NORMAL),
                    Arguments.of(2, ExpulsionStatus.ADVANCE),
                    Arguments.of(3, ExpulsionStatus.INTERVIEW),
                    Arguments.of(6, ExpulsionStatus.EXPULSION)
            );
        }

    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {
    }

}
