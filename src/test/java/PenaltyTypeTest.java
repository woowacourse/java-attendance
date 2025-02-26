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
        assertThat(PenaltyTypeFactory.create(0, absenceCount)).isEqualTo("경고");
    }

    @Test
    @DisplayName("결석 3회 이상은 면담 대상이다.")
    void test2() {
        // given
        int absenceCount = 3;

        // when & then
        assertThat(PenaltyTypeFactory.create(0, absenceCount)).isEqualTo("면담");
    }

    @Test
    @DisplayName("결석 5회 초과는 제적 대상이다.")
    void test3() {
        // given
        int absenceCount = 6;

        // when & then
        assertThat(PenaltyTypeFactory.create(0, absenceCount)).isEqualTo("제적");
    }

    @Test
    @DisplayName("지각 3회는 결석 1회로 간주하여 패널티의 종류를 결정한다.")
    void test4() {
        // given
        int absenceCount = 1;
        int lateCount = 3;

        // when & then
        assertThat(PenaltyTypeFactory.create(lateCount, absenceCount)).isEqualTo("경고");
    }
}
