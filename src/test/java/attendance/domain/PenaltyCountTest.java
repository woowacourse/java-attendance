package attendance.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PenaltyCountTest {

    @Test
    void 지각과_결석_횟수를_계산한다() {
        // given
        Map<AttendanceStatus, Integer> statusCounts = Map.of(
            AttendanceStatus.PRESENCE, 1,
            AttendanceStatus.ABSENCE, 2,
            AttendanceStatus.LATE, 3
        );
        PenaltyCount penaltyCount = new PenaltyCount(statusCounts);

        // when
        int weightedLateAndAbsenceCount = penaltyCount.calculateWeightedLateAndAbsenceCount();

        // then
        assertThat(weightedLateAndAbsenceCount).isEqualTo(3);
    }
}
