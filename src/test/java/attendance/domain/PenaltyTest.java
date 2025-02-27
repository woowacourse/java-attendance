package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("징계 테스트")
public class PenaltyTest {

    @Test
    void 지각이_3회이면_결석1회로_간주하여_결석횟수를_반환한다() {
        int late = 6;
        int absent = 1;

        assertThat(Penalty.calculateTotalAbsent(late, absent)).isEqualTo(3);
    }

    @Test
    void 총결석횟수가_5회를_초과하면_제적을_반환한다() {
        assertThat(Penalty.determine(0, 6)).isEqualTo(Penalty.REMOVAL);
    }

    @Test
    void 총결석횟수가_3회이상이고_5회이하이면_면담을_반환한다() {
        assertAll(
                () -> assertThat(Penalty.determine(0, 3)).isEqualTo(Penalty.INTERVIEW),
                () -> assertThat(Penalty.determine(0, 5)).isEqualTo(Penalty.INTERVIEW)
        );
    }

    @Test
    void 총결석횟수가_2회라면_경고를_반환한다() {
        assertThat(Penalty.determine(0, 2)).isEqualTo(Penalty.WARNING);
    }
}
