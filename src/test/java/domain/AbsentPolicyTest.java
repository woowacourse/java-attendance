package domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AbsentPolicyTest {

    public static Stream<Arguments> getNoneCounts() {
        return Stream.of(
                Arguments.of(1, 1),
                Arguments.of(1, 2),
                Arguments.of(0, 3),
                Arguments.of(0, 4),
                Arguments.of(0, 5)
        );
    }

    public static Stream<Arguments> getWarningCounts() {
        return Stream.of(
                Arguments.of(2, 1),
                Arguments.of(2, 2)
        );
    }

    public static Stream<Arguments> getInterviewCounts() {
        return Stream.of(
                Arguments.of(3, 1),
                Arguments.of(3, 2),
                Arguments.of(3, 3),
                Arguments.of(4, 1),
                Arguments.of(4, 2),
                Arguments.of(4, 3),
                Arguments.of(5, 1),
                Arguments.of(5, 2)
        );
    }

    public static Stream<Arguments> getExpulsionCounts() {
        return Stream.of(
                Arguments.of(5, 3),
                Arguments.of(6, 0),
                Arguments.of(6, 3),
                Arguments.of(6, 1),
                Arguments.of(7, 2)
        );
    }

    @ParameterizedTest
    @DisplayName("결석 횟수와 지각 횟수에 따라 대상자가 아님을 확인한다.")
    @MethodSource("getNoneCounts")
    void absent_count_late_count_then_none(int absentCount, int lateCount) {
        Assertions.assertThat(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount))
                .isEqualTo(AbsentPolicy.NONE);
    }

    @ParameterizedTest
    @DisplayName("결석 횟수와 지각 횟수에 따라 경고 대상자임을 계산한다.")
    @MethodSource("getWarningCounts")
    void absent_count_late_count_then_warning(int absentCount, int lateCount) {
        Assertions.assertThat(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount))
                .isEqualTo(AbsentPolicy.WARNING);
    }

    @ParameterizedTest
    @DisplayName("결석 횟수와 지각 횟수에 따라 면담 대상자임을 계산한다.")
    @MethodSource("getInterviewCounts")
    void absent_count_late_count_then_interview(int absentCount, int lateCount) {
        Assertions.assertThat(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount))
                .isEqualTo(AbsentPolicy.INTERVIEW);
    }

    @ParameterizedTest
    @DisplayName("결석 횟수와 지각 횟수에 따라 제적 대상자임을 계산한다.")
    @MethodSource("getExpulsionCounts")
    void absent_count_late_count_then_expulsion(int absentCount, int lateCount) {
        Assertions.assertThat(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount))
                .isEqualTo(AbsentPolicy.EXPULSION);
    }

    @Test
    @DisplayName("제적 위험자가 아님을 확인한다.")
    void check_not_risk_of_expulsion() {
        Assertions.assertThat(AbsentPolicy.isRiskOfExpulsion(AbsentPolicy.EXPULSION))
                .isFalse();
        Assertions.assertThat(AbsentPolicy.isRiskOfExpulsion(AbsentPolicy.NONE))
                .isFalse();
    }

    @Test
    @DisplayName("제적 위험자임을 확인한다.")
    void check_risk_of_expulsion() {
        Assertions.assertThat(AbsentPolicy.isRiskOfExpulsion(AbsentPolicy.INTERVIEW))
                .isTrue();
        Assertions.assertThat(AbsentPolicy.isRiskOfExpulsion(AbsentPolicy.WARNING))
                .isTrue();
    }
}
