package domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ExpelStatusTest {

    @Test
    void 경고_상태_확인() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        attendanceStatuses.put(AttendanceStatus.ATTEND, 3);
        attendanceStatuses.put(AttendanceStatus.LATE, 3);
        attendanceStatuses.put(AttendanceStatus.ABSENT, 1);
        attendanceStatuses.put(AttendanceStatus.UNATTEND, 0);

        assertThat(ExpelStatus.determineExpelStatus(attendanceStatuses))
                .isEqualTo(ExpelStatus.WARNING);
    }

    @Test
    void 면담_상태_확인() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        attendanceStatuses.put(AttendanceStatus.ATTEND, 3);
        attendanceStatuses.put(AttendanceStatus.LATE, 3);
        attendanceStatuses.put(AttendanceStatus.ABSENT, 2);
        attendanceStatuses.put(AttendanceStatus.UNATTEND, 0);

        assertThat(ExpelStatus.determineExpelStatus(attendanceStatuses))
                .isEqualTo(ExpelStatus.INTERVIEW);
    }

    @Test
    void 제적_상태_확인() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        attendanceStatuses.put(AttendanceStatus.ATTEND, 3);
        attendanceStatuses.put(AttendanceStatus.LATE, 3);
        attendanceStatuses.put(AttendanceStatus.ABSENT, 5);
        attendanceStatuses.put(AttendanceStatus.UNATTEND, 0);

        assertThat(ExpelStatus.determineExpelStatus(attendanceStatuses))
                .isEqualTo(ExpelStatus.EXPULSION);
    }
}
