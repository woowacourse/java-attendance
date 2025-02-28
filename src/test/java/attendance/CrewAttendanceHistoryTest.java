package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatuses;
import attendance.domain.CrewAttendanceHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceHistoryTest {

    @Test
    @DisplayName("출석 내역 테스트")
    void testAttendancesHistories() {
        String src = "/testAttendanceHistory.csv";
        String name = "빙티";
        int lateCount = 2;
        int attendanceCount = 5;

        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());

        CrewAttendanceHistory crewAttendanceHistory = attendanceManager
                .crewAttendanceHistory(name);
        AttendanceStatuses attendanceStatuses = crewAttendanceHistory.attendanceStatuses();

        assertThat(attendanceStatuses.lateCount()).isEqualTo(lateCount);
        assertThat(attendanceStatuses.attendanceCount()).isEqualTo(attendanceCount);
    }

    @Test
    @DisplayName("출석 제적 상태 테스트")
    void testAttendancesDismissStatus() {
        String src = "/testAttendanceHistory.csv";
        String name = "빙티";
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());

        CrewAttendanceHistory crewAttendanceHistory = attendanceManager.crewAttendanceHistory(name);
        AttendanceStatuses attendanceStatuses = crewAttendanceHistory.attendanceStatuses();
        AttendanceDismissStatus attendanceDismissStatus = attendanceStatuses.calculateAttendanceDismiss();
        assertThat(attendanceDismissStatus).isEqualTo(AttendanceDismissStatus.DISMISS);
    }
}
