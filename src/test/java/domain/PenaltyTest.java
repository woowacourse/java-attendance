package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("제적 위험자 결정에 대한 테스트")
public class PenaltyTest {

    @Test
    void 지각_3회를_결석1회로_계산한다() {
        var noLateTwoAbsences = Penalty.determine(0, 2);
        var threeLatesOneAbsence = Penalty.determine(3, 1);

        assertThat(noLateTwoAbsences).isEqualTo(threeLatesOneAbsence);
    }

    @Test
    void 결석이_2회_미만이면_아무_패널티도_없다() {
        var penalty = Penalty.determine(0, 1);

        assertThat(penalty).isEqualTo(Penalty.NONE);
    }

    @Test
    void 결석이_2회_이상이면_경고이다() {
        var penalty = Penalty.determine(0, 2);

        assertThat(penalty).isEqualTo(Penalty.WARNING);
    }

    @Test
    void 결석이_3회_이상이면_면담이다() {
        var penalty = Penalty.determine(0, 3);

        assertThat(penalty).isEqualTo(Penalty.INTERVIEW);
    }

    @Test
    void 결석이_5회를_초과하면_제적이다() {
        var penalty = Penalty.determine(0, 6);

        assertThat(penalty).isEqualTo(Penalty.LEAVE);
    }
}
