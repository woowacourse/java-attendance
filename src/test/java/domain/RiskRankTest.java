package domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RiskRankTest {
    private static Stream<Arguments> fromCases() {
        return Stream.of(
                Arguments.of(0, 0, RiskRank.NOT_MANAGED),
                Arguments.of(2, 1, RiskRank.NOT_MANAGED),
                Arguments.of(0, 2, RiskRank.WARNING),
                Arguments.of(2, 2, RiskRank.WARNING),
                Arguments.of(0, 3, RiskRank.INTERVIEW),
                Arguments.of(0, 4, RiskRank.INTERVIEW),
                Arguments.of(0, 5, RiskRank.INTERVIEW),
                Arguments.of(0, 6, RiskRank.EXPELLED)
        );
    }

    private static Stream<Arguments> getRiskCountCases() {
        return Stream.of(
                Arguments.of(3, 1, 2),
                Arguments.of(5, 1, 2),
                Arguments.of(6, 1, 3),
                Arguments.of(8, 1, 3),
                Arguments.of(9, 1, 4)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "fromCases")
    @DisplayName("결석 횟수에 맞는 제적 위험 등급을 반환한다")
    void from_test(int lateCount, int absentCount, RiskRank expected) {
        RiskRank actual = RiskRank.from(new AttendanceStatusCount(0, lateCount, absentCount));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource(value = "getRiskCountCases")
    @DisplayName("지각 횟수 3회를 결석 횟수 1회로 계산하여 총 결석 횟수를 계산한다")
    void getRiskCount_test(int lateCount, int absentCount, int riskCount) {
        Assertions.assertThat(RiskRank.getRiskCount(lateCount, absentCount)).isEqualTo(riskCount);
    }
}