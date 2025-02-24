package attendance.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AttendancePenaltyTest {

    @Test
    void 면담_대상자를_확인한다() {
        Map<AttendanceStatus, Integer> result = Map.of(
            AttendanceStatus.PRESENCE,3,
            AttendanceStatus.LATE,5 ,
            AttendanceStatus.ABSENCE,2);
        
        assertThat(AttendancePenalty.find(result).getMessage()).isEqualTo("면담");
    }

    @Test
    void 제적_대상자를_확인한다() {
        Map<AttendanceStatus, Integer> result = Map.of(
            AttendanceStatus.PRESENCE,3,
            AttendanceStatus.LATE,15 ,
            AttendanceStatus.ABSENCE,1);

        assertThat(AttendancePenalty.find(result).getMessage()).isEqualTo("제적");
    }

    @Test
    void 경고_대상자를_확인한다() {
        Map<AttendanceStatus, Integer> result = Map.of(
            AttendanceStatus.PRESENCE,3,
            AttendanceStatus.LATE,6 ,
            AttendanceStatus.ABSENCE,0);

        assertThat(AttendancePenalty.find(result).getMessage()).isEqualTo("경고");
    }
}
