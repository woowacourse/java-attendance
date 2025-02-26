package attendance.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendancePenaltyTest {

    @Test
    void 경고_대상_여부를_확인한다() {
        // given
        int weightedLateAndAbsenceCount = 6;

        // when
        AttendancePenalty penalty = AttendancePenalty.findPenalty(weightedLateAndAbsenceCount);

        // then
        assertThat(penalty).isEqualTo(AttendancePenalty.EXPULSION);
    }
}
