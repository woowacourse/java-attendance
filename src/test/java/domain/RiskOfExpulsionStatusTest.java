package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RiskOfExpulsionStatusTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("주어진 값으로, 제적 대상자의 상태를 반환하라")
        @ParameterizedTest
        @MethodSource("provideAbsenceCountAndRiskOfExpulsionStatus")
        public void calculateRiskOfExpulsionStatus(final int absenceCount, final RiskOfExpulsionStatus expected)
                throws Exception {
            // given & when
            final RiskOfExpulsionStatus actual = RiskOfExpulsionStatus.calculateRiskOfExpulsionStatus(
                    absenceCount);

            // then
            assertThat(actual).isEqualByComparingTo(expected);
        }

        private static Stream<Arguments> provideAbsenceCountAndRiskOfExpulsionStatus() {
            return Stream.of(
                    Arguments.of(1, RiskOfExpulsionStatus.NORMAL),
                    Arguments.of(2, RiskOfExpulsionStatus.WARNING),
                    Arguments.of(3, RiskOfExpulsionStatus.INTERVIEW),
                    Arguments.of(6, RiskOfExpulsionStatus.EXPULSION)
            );
        }

    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("주어진 값이 음수라면 예외가 발생한다.")
        @Test
        public void calculateRiskOfExpulsionStatus() throws Exception {
            // given
            final int negativeNumber = -1;

            // when & then
            assertThatThrownBy(() -> RiskOfExpulsionStatus.calculateRiskOfExpulsionStatus(negativeNumber))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
