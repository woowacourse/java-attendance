package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PenaltyTest {


    public static Stream<Arguments> providePenaltyPointAndExpected() {
        return Stream.of(
                Arguments.of(6, Penalty.EXPULSION),
                Arguments.of(5, Penalty.INTERVIEW),
                Arguments.of(2, Penalty.WARNING),
                Arguments.of(1, null)
        );
    }

    @ParameterizedTest
    @MethodSource("providePenaltyPointAndExpected")
    void 패널티_점수에_따라_다른_패널티를_반환한다(Integer penaltyPoint, Penalty expected) {
        assertThat(Penalty.getPenaltyOf(penaltyPoint)).isEqualTo(expected);
    }

}