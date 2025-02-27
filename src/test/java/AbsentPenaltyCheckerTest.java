import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AbsentPenaltyCheckerTest {

    @DisplayName("결석이 2회 이상이면 경고 대상자이다.")
    @Test
    void test1() {
        // given
        AbsentPenaltyChecker checker = new AbsentPenaltyChecker();
        final var absentCount = 2;

        // when
        final var absentPenalty = checker.check(absentCount);

        // then
        Assertions.assertThat(absentPenalty)
                .isEqualTo(AbsentPenalty.WARNING);
    }
}
