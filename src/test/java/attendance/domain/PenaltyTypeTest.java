package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PenaltyTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1,3, WARNING", "2,3, COUNSELING", "5,3, EXPULSION"})
    public void 패널티_생성(int absenceCounts, int lateCount, PenaltyType penaltyType) {
        //when & then
        Assertions.assertThat(PenaltyType.fetchPenaltyType(absenceCounts, lateCount)).isEqualTo(penaltyType);
    }
}
