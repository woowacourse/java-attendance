package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("징계 테스트")
public class PenaltyTest {

    @ParameterizedTest
    @CsvSource(value = {"6:1:3", "3:0:1", "9:2:5"}, delimiterString = ":")
    void 지각이_3회이면_결석1회로_간주하여_결석횟수를_반환한다(int late, int absent, int expectedAbsent) {
        assertThat(Penalty.calculateTotalAbsent(late, absent)).isEqualTo(expectedAbsent);
    }

    @ParameterizedTest
    @CsvSource(value = {"0:6", "3:5", "6:4"}, delimiterString = ":")
    void 총결석횟수가_5회를_초과하면_제적을_반환한다(int late, int absent) {
        assertThat(Penalty.determine(late, absent)).isEqualTo(Penalty.REMOVAL);
    }

    @ParameterizedTest
    @CsvSource(value = {"0:3", "0:5", "3:2", "6:3"}, delimiterString = ":")
    void 총결석횟수가_3회이상이고_5회이하이면_면담을_반환한다(int late, int absent) {
        assertThat(Penalty.determine(late, absent)).isEqualTo(Penalty.INTERVIEW);
    }

    @ParameterizedTest
    @CsvSource(value = {"0:2", "3:1", "6:0"}, delimiterString = ":")
    void 총결석횟수가_2회라면_경고를_반환한다(int late, int absent) {
        assertThat(Penalty.determine(late, absent)).isEqualTo(Penalty.WARNING);
    }
}
