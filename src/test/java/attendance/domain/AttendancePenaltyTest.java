package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendancePenaltyTest {

    @Test
    void 누적_결석이_6이상이면_제적을_반환한다() {
        // given
        int weightedLateAndAbsenceCount = 6;

        // when
        AttendancePenalty penalty = AttendancePenalty.findPenalty(weightedLateAndAbsenceCount);

        // then
        assertThat(penalty).isEqualTo(AttendancePenalty.EXPULSION);
    }

    @Test
    void 누적_결석이_3이상_6미만이면_면담을_반환한다() {
        // given
        int weightedLateAndAbsenceCount = 3;

        // when
        AttendancePenalty penalty = AttendancePenalty.findPenalty(weightedLateAndAbsenceCount);

        // then
        assertThat(penalty).isEqualTo(AttendancePenalty.COUNSELING);
    }

    @Test
    void 누적_결석이_2면_경고를_반환한다() {
        // given
        int weightedLateAndAbsenceCount = 2;

        // when
        AttendancePenalty penalty = AttendancePenalty.findPenalty(weightedLateAndAbsenceCount);

        // then
        assertThat(penalty).isEqualTo(AttendancePenalty.WARNING);
    }

    @Test
    void 누적_결석이_2미만이면_경고없음을_반환한다() {
        // given
        int weightedLateAndAbsenceCount = 1;

        // when
        AttendancePenalty penalty = AttendancePenalty.findPenalty(weightedLateAndAbsenceCount);

        // then
        assertThat(penalty).isEqualTo(AttendancePenalty.NONE);
    }
}
