import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
input : 지각 3회 -> 결석 1회
1. 제적 테스트
2. 면담 테스트
3. 경고 테스트
4. 정상 테스트
 */
public class ExpellPolicyTest {
    @ParameterizedTest
    @DisplayName("결석 회수가 5회 초과이면 제적 대상자이다")
    @MethodSource("provideLateCountAndAbsentCountForIsExpell")
    public void expellPolicyTest(int lateCount, int absentCount) {
        //given
        ExpellPolicy expellPolicy = new ExpellPolicy();

        //when-then
        assertThat(expellPolicy.checkExpellStatus(lateCount, absentCount)).isEqualTo("제적");
    }

    private static Stream<Arguments> provideLateCountAndAbsentCountForIsExpell() {
        return Stream.of(
                Arguments.of(0,6),
                Arguments.of(1,6),
                Arguments.of(2,6),
                Arguments.of(3,5)
        );
    }

    @ParameterizedTest
    @DisplayName("결석 회수가 3회 이상이면 면담 대상자이다")
    @MethodSource("provideLateCountAndAbsentCountForIsInterview")
    public void interviewPolicyTest(int lateCount, int absentCount) {
        //given
        ExpellPolicy expellPolicy = new ExpellPolicy();

        //when-then
        assertThat(expellPolicy.checkExpellStatus(lateCount, absentCount)).isEqualTo("면담");
    }

    private static Stream<Arguments> provideLateCountAndAbsentCountForIsInterview() {
        return Stream.of(
                Arguments.of(0,6),
                Arguments.of(1,6),
                Arguments.of(2,6),
                Arguments.of(3,5)
        );
    }
}
