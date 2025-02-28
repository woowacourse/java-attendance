import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
