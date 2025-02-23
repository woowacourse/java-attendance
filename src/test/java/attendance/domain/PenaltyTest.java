package attendance.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PenaltyTest {

    @CsvSource(value = {"6:0:REMOVAL", "3:0:INTERVIEW", "2:0:WARNING", "1:2:NONE"}, delimiterString = ":")
    @ParameterizedTest
    void 결석횟수와_지각횟수에_따라_맞는_패널티상태를_반환한다(int absenceCount, int lateCount, Penalty expectedPenalty) {
        assertThat(Penalty.determine(absenceCount, lateCount)).isEqualTo(expectedPenalty);
    }

    @CsvSource(value = {"5:3:REMOVAL", "2:3:INTERVIEW", "0:6:WARNING", "0:3:NONE"}, delimiterString = ":")
    @ParameterizedTest
    void 지각3회를_결석1회로_간주하여_패널티상태를_반환한다(int absenceCount, int lateCount, Penalty expectedPenalty) {
        assertThat(Penalty.determine(absenceCount, lateCount)).isEqualTo(expectedPenalty);
    }
}
