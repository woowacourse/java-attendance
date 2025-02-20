package domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RiskStatusTest {
    @Test
    void Risk가_NONE이면_false를_반환한다() {
        Assertions.assertThat(RiskStatus.NONE.hasRisk()).isEqualTo(false);
    }

    @Test
    void Risk가_NONE이_아니면_false를_반환한다() {
        Assertions.assertThat(RiskStatus.COUNSELING.hasRisk()).isEqualTo(true);
    }
}