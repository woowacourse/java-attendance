package domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RiskRankTest {

    @Test
    @DisplayName("결석 횟수에 맞는 제적 위험 등급을 반환한다")
    void from_test() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(RiskRank.of(0, 0)).isEqualByComparingTo(RiskRank.NOT_MANAGED);
            softAssertions.assertThat(RiskRank.of(0, 1)).isEqualByComparingTo(RiskRank.NOT_MANAGED);
            softAssertions.assertThat(RiskRank.of(0, 2)).isEqualByComparingTo(RiskRank.WARNING);
            softAssertions.assertThat(RiskRank.of(0, 3)).isEqualByComparingTo(RiskRank.INTERVIEW);
            softAssertions.assertThat(RiskRank.of(0, 4)).isEqualByComparingTo(RiskRank.INTERVIEW);
            softAssertions.assertThat(RiskRank.of(0, 5)).isEqualByComparingTo(RiskRank.INTERVIEW);
            softAssertions.assertThat(RiskRank.of(0, 6)).isEqualByComparingTo(RiskRank.EXPELLED);
        });
    }

    @Test
    @DisplayName("지각 횟수 3회를 결석 횟수 1회로 계산한다")
    void calculateRiskCount_test() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(RiskRank.calculateRiskCount(3, 1)).isEqualTo(2);
            softAssertions.assertThat(RiskRank.calculateRiskCount(4, 1)).isEqualTo(2);
            softAssertions.assertThat(RiskRank.calculateRiskCount(5, 1)).isEqualTo(2);
            softAssertions.assertThat(RiskRank.calculateRiskCount(6, 1)).isEqualTo(3);
            softAssertions.assertThat(RiskRank.calculateRiskCount(7, 1)).isEqualTo(3);
        });
    }
}