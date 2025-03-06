package attendance.domain;

import static attendance.domain.RiskAtExpulsion.EXPULSION;
import static attendance.domain.RiskAtExpulsion.INTERVIEW;
import static attendance.domain.RiskAtExpulsion.NOT_APPLICABLE;
import static attendance.domain.RiskAtExpulsion.WARNING;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RiskAtExpulsionTest {

    @ParameterizedTest
    @MethodSource
    void 지각을_결석으로_간주하여_제적_위험자를_판별한다(final int absentCount, final int lateCount, final RiskAtExpulsion expected) {
        assertThat(RiskAtExpulsion.of(absentCount, lateCount)).isEqualTo(expected);
    }

    private static Stream<Arguments> 지각을_결석으로_간주하여_제적_위험자를_판별한다() {
        return Stream.of(
                Arguments.of(1, 0, NOT_APPLICABLE),

                Arguments.of(2, 0, WARNING),
                Arguments.of(2, 1, WARNING),
                Arguments.of(0, 6, WARNING),
                Arguments.of(0, 7, WARNING),
                Arguments.of(1, 3, WARNING),

                Arguments.of(3, 0, INTERVIEW),
                Arguments.of(3, 1, INTERVIEW),
                Arguments.of(2, 3, INTERVIEW),
                Arguments.of(2, 4, INTERVIEW),
                Arguments.of(4, 0, INTERVIEW),
                Arguments.of(5, 0, INTERVIEW),
                Arguments.of(5, 1, INTERVIEW),

                Arguments.of(6, 0, EXPULSION),
                Arguments.of(6, 1, EXPULSION),
                Arguments.of(0, 18, EXPULSION),
                Arguments.of(0, 19, EXPULSION)
        );
    }
}
