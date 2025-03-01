package attendance.domain.risk;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RiskTypeTest {

    @DisplayName("결석과 지각횟수를 통해 적절한 제적 위험도를 계산할 수 있다")
    @ParameterizedTest
    @MethodSource
    void 결석과_지각횟수를_통해_적절한_제적_위험도를_계산할_수_있다(
            int absenceCount,
            int lateCount,
            RiskType expectedType
    ) {
        RiskType actualType = RiskType.parse(absenceCount, lateCount);
        Assertions.assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 결석과_지각횟수를_통해_적절한_제적_위험도를_계산할_수_있다() {
        return Stream.of(
                Arguments.of(0, 0, RiskType.NONE),
                Arguments.of(1, 3, RiskType.WARNING),
                Arguments.of(2, 0, RiskType.WARNING),
                Arguments.of(2, 3, RiskType.COUNSELING),
                Arguments.of(3, 0, RiskType.COUNSELING),
                Arguments.of(4, 3, RiskType.EXPULSION),
                Arguments.of(5, 0, RiskType.EXPULSION),
                Arguments.of(6, 0, RiskType.EXPULSION)
        );
    }
}