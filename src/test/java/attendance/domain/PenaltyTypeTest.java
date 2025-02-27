package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PenaltyTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"2, WARNING", "3, COUNSELING", "6, EXPULSION"})
    public void 패널티_생성(int penaltyCount, PenaltyType penaltyType) {
        //when & then
        Assertions.assertThat(PenaltyType.fetchPenaltyType(penaltyCount)).isEqualTo(penaltyType);
    }
}
