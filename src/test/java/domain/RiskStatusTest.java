package domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RiskStatusTest {
    @Test
    void Risk가_NONE이면_false를_반환한다() {
        assertThat(RiskStatus.NONE.hasRisk()).isEqualTo(false);
    }

    @Test
    void Risk가_NONE이_아니면_false를_반환한다() {
        assertThat(RiskStatus.COUNSELING.hasRisk()).isEqualTo(true);
    }

    @Test
    void 결석횟수와_지각횟수로_총_결석횟수를_계산한다() {
        final int absenceCount = 5;
        final int tardyCount = 5;

        assertThat(RiskStatus.calculateTotalAbsenceCount(absenceCount, tardyCount)).isEqualTo(6);
    }
}