package domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RiskRankTest {

    @Test
    @DisplayName("결석 횟수에 맞는 제적 위험 등급을 반환한다")
    void fromTest() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(RiskRank.from(0)).isEqualByComparingTo(RiskRank.NOT_MANAGED);
            softAssertions.assertThat(RiskRank.from(1)).isEqualByComparingTo(RiskRank.NOT_MANAGED);
            softAssertions.assertThat(RiskRank.from(2)).isEqualByComparingTo(RiskRank.WARNING);
            softAssertions.assertThat(RiskRank.from(3)).isEqualByComparingTo(RiskRank.INTERVIEW);
            softAssertions.assertThat(RiskRank.from(4)).isEqualByComparingTo(RiskRank.INTERVIEW);
            softAssertions.assertThat(RiskRank.from(5)).isEqualByComparingTo(RiskRank.INTERVIEW);
            softAssertions.assertThat(RiskRank.from(6)).isEqualByComparingTo(RiskRank.EXPELLED);
        });
    }
}