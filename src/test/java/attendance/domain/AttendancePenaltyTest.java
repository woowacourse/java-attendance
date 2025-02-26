package attendance.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendancePenaltyTest {

    @Test
    void 경고_대상_여부를_확인한다() {
        // given
        Map<AttendanceStatus, Integer> attendanceStatusCounts = Map.of(
            AttendanceStatus.PRESENCE,1,
            AttendanceStatus.LATE, 3,
            AttendanceStatus.ABSENCE, 5
        );

        // when
        AttendancePenalty penalty = AttendancePenalty.findPenalty(attendanceStatusCounts);

        // then
        assertThat(penalty).isEqualTo(AttendancePenalty.EXPULSION);
    }
}
