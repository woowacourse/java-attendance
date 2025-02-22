package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PenaltyTest {

    @ParameterizedTest(name = "결석 {0}회, 지각 {1}회이면 Penalty = {2}")
    @CsvSource({
        "0,0,NONE",
        "1,2,NONE",
        "1,3,WARNING",
        "2,0,WARNING",
        "2,3,INTERVIEW",
        "4,0,INTERVIEW",
        "5,0,INTERVIEW",
        "5,3,REMOVAL",
        "6,0,REMOVAL"
    })
    void 지각횟수와_결석횟수로_패널티를_결정한다(int absence, int late, Penalty expected) {
        Penalty penalty = Penalty.determine(absence, late);
        assertThat(penalty).isEqualTo(expected);
    }
}
