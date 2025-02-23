package attendance.domain.risk;

import static attendance.domain.risk.RiskType.COUNSELING;
import static attendance.domain.risk.RiskType.NONE;
import static attendance.domain.risk.RiskType.WARNING;
import static attendance.domain.risk.RiskType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RiskStatisticTest {

    @DisplayName("결석 횟수와 지각 횟수로 제적 위험도를 계산한다")
    @ParameterizedTest
    @MethodSource()
    void 결석_횟수와_지각_횟수로_제적_위험도를_계산한다(int expulsion, int late, RiskType type) {
        RiskStatistic statistic = new RiskStatistic("쿠키", 10, expulsion, late);

        assertThat(statistic.getRiskType())
                .isEqualTo(type);
    }

    static Stream<Arguments> 결석_횟수와_지각_횟수로_제적_위험도를_계산한다() {
        return Stream.of(
                Arguments.of(0, 0, NONE),
                Arguments.of(0, 5, NONE),
                Arguments.of(2, 0, WARNING),
                Arguments.of(0, 8, WARNING),
                Arguments.of(3, 0, COUNSELING),
                Arguments.of(0, 15, COUNSELING),
                Arguments.of(6, 0, WITHDRAWAL),
                Arguments.of(0, 18, WITHDRAWAL)
        );
    }
}