package domain.policy;

import static domain.policy.ExpellState.EXPELL;
import static domain.policy.ExpellState.INTERVIEW;
import static domain.policy.ExpellState.WARNING;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ExpellStateTest {

    @ParameterizedTest
    @DisplayName("결석 회수가 5회 초과이면 제적 대상자이다")
    @MethodSource("provideLateCountAndAbsentCountForIsExpell")
    public void expellPolicyTest(int lateCount, int absentCount) {
        assertThat(ExpellState.checkExpellStatus(lateCount, absentCount)).isEqualTo(EXPELL);
    }

    private static Stream<Arguments> provideLateCountAndAbsentCountForIsExpell() {
        return Stream.of(
                Arguments.of(0, 6),
                Arguments.of(1, 6),
                Arguments.of(2, 6),
                Arguments.of(3, 5)
        );
    }

    @ParameterizedTest
    @DisplayName("결석 회수가 3회 이상이면 면담 대상자이다")
    @MethodSource("provideLateCountAndAbsentCountForIsInterview")
    public void interviewPolicyTest(int lateCount, int absentCount) {
        assertThat(ExpellState.checkExpellStatus(lateCount, absentCount)).isEqualTo(INTERVIEW);
    }

    private static Stream<Arguments> provideLateCountAndAbsentCountForIsInterview() {
        return Stream.of(
                Arguments.of(0, 3),
                Arguments.of(0, 4),
                Arguments.of(0, 5),
                Arguments.of(3, 2)
        );
    }

    @ParameterizedTest
    @DisplayName("결석 회수가 2회 이상이면 경고 대상자이다")
    @MethodSource("provideLateCountAndAbsentCountForIsWarning")
    public void warningPolicyTest(int lateCount, int absentCount) {
        assertThat(ExpellState.checkExpellStatus(lateCount, absentCount)).isEqualTo(WARNING);
    }

    private static Stream<Arguments> provideLateCountAndAbsentCountForIsWarning() {
        return Stream.of(
                Arguments.of(0, 2),
                Arguments.of(3, 1),
                Arguments.of(6, 0)
        );
    }
}
