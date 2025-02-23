package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("결석 정책 테스트")
public class AbsentPolicyTest {

    @ParameterizedTest
    @DisplayName("제적 위험 여부를 판단할 수 있다. 면담 대상자 또는 경고 대상자일 경우 제적 위험 대상자이다.")
    @MethodSource("provideAbsentPolicyForExpulsion")
    void expulsionTest(AbsentPolicy absentPolicy, boolean isExpulsion) {
        assertThat(AbsentPolicy.isNotRiskOfExpulsion(absentPolicy))
                .isEqualTo(isExpulsion);
    }

    private static Stream<Arguments> provideAbsentPolicyForExpulsion() {
        return Stream.of(
                Arguments.of(AbsentPolicy.EXPULSION, true),
                Arguments.of(AbsentPolicy.INTERVIEW, false),
                Arguments.of(AbsentPolicy.WARNING, false),
                Arguments.of(AbsentPolicy.NONE, true)
        );
    }

    @ParameterizedTest
    @DisplayName("제적 위험 상태를 판단할 수 있다.")
    @MethodSource("provideAbsentCountAndLateCountForExpulsion")
    void absentPolicyTest(int absentCount, int lateCount, AbsentPolicy absentPolicy) {
        assertThat(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount))
                .isEqualTo(absentPolicy);
    }

    private static Stream<Arguments> provideAbsentCountAndLateCountForExpulsion() {
        return Stream.of(
                Arguments.of(3, 4, AbsentPolicy.INTERVIEW),
                Arguments.of(0, 9, AbsentPolicy.INTERVIEW),
                Arguments.of(1, 6, AbsentPolicy.INTERVIEW),
                Arguments.of(0, 6, AbsentPolicy.WARNING),
                Arguments.of(6, 0, AbsentPolicy.EXPULSION),
                Arguments.of(0, 0, AbsentPolicy.NONE)
        );
    }
}
