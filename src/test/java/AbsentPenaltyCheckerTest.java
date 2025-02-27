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
        final var absentPenalty = checker.determine(absentCount);

        // then
        Assertions.assertThat(absentPenalty)
                .isEqualTo(AbsentPenalty.WARNING);
    }

    @DisplayName("결석이 3회 이상이면 면담 대상자이다.")
    @Test
    void test2() {
        // given
        AbsentPenaltyChecker checker = new AbsentPenaltyChecker();
        final var absentCount = 3;

        // when
        final var absentPenalty = checker.determine(absentCount);

        // then
        Assertions.assertThat(absentPenalty)
                .isEqualTo(AbsentPenalty.INTERVIEW);
    }

    @DisplayName("결석이 5회 초과이면 제적 대상자이다.")
    @Test
    void test3() {
        // given
        AbsentPenaltyChecker checker = new AbsentPenaltyChecker();
        final var absentCount = 6;

        // when
        final var absentPenalty = checker.determine(absentCount);

        // then
        Assertions.assertThat(absentPenalty)
                .isEqualTo(AbsentPenalty.DISMISSAL);
    }

    @DisplayName("결석이 2회 미만이면 어떤 대상자도 아니다.")
    @Test
    void test4() {
        // given
        AbsentPenaltyChecker checker = new AbsentPenaltyChecker();
        final var absentCount = 1;

        // when
        final var absentPenalty = checker.determine(absentCount);

        // then
        Assertions.assertThat(absentPenalty)
                .isEqualTo(AbsentPenalty.NONE);
    }
}
