import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import type.PenaltyType;

public class PenaltyTypeTest {
    @Test
    @DisplayName("결석 2회 이상은 경고 대상이다.")
    void test1() {
        // given
        int absenceCount = 2;

        // when & then
        assertThat(PenaltyType.findByAbsenceCount(absenceCount)).isEqualTo(PenaltyType.WARNING);
    }

    @Test
    @DisplayName("결석 3회 이상은 면담 대상이다.")
    void test2() {
        // given
        int absenceCount = 3;

        // when & then
        assertThat(PenaltyType.findByAbsenceCount(absenceCount)).isEqualTo(PenaltyType.ONE_ON_ONE);
    }

    @Test
    @DisplayName("결석 5회 초과는 제적 대상이다.")
    void test3() {
        // given
        int absenceCount = 6;

        // when & then
        assertThat(PenaltyType.findByAbsenceCount(absenceCount)).isEqualTo(PenaltyType.BAN);
    }

    @DisplayName("패널티 타입이 DEFAULT 가 아닌 경우를 판단한다")
    @ParameterizedTest
    @EnumSource(PenaltyType.class)
    void test4(PenaltyType penaltyType) {
        if (penaltyType == PenaltyType.DEFAULT) {
            assertThat(penaltyType.isAtExpulsionCandidateState()).isFalse();
            return;
        }
        assertThat(penaltyType.isAtExpulsionCandidateState()).isTrue();
    }

    @Test
    @DisplayName("결석 횟수가 5회인 경우 면담을 반환한다")
    void test5() {
        // given
        PenaltyType actual = PenaltyType.findByAbsenceCount(5);

        // when & then
        assertThat(actual).isEqualTo(PenaltyType.ONE_ON_ONE);
    }
}
