package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatuses;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    @DisplayName("출석 내역 테스트")
    void testAttendancesHistories() {
        String src = "/testAttendanceHistory.csv";
        String name = "빙티";
        List<String> attendanceHistories = List.of("12월 02일 월요일 13:00 (출석)",
                "12월 03일 화요일 10:07 (지각)",
                "12월 04일 수요일 10:02 (출석)",
                "12월 05일 목요일 10:06 (지각)",
                "12월 06일 금요일 10:01 (출석)",
                "12월 09일 월요일 --:-- (결석)",
                "12월 10일 화요일 10:03 (출석)",
                "12월 11일 수요일 --:-- (결석)",
                "12월 12일 목요일 --:-- (결석)",
                "12월 13일 금요일 10:02 (출석)");
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());

        List<String> histories = attendanceManager
                .crewAttendanceHistory(name)
                .attendanceHistories();
        for (String attendanceHistory : attendanceHistories) {
            assertThat(histories).contains(attendanceHistory);
        }
    }

    @Test
    @DisplayName("출석 제적 상태 테스트")
    void testAttendancesDismissStatus() {
        String src = "/testAttendanceHistory.csv";
        String name = "빙티";
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());

        AttendanceHistory attendanceHistory = attendanceManager.crewAttendanceHistory(name);
        AttendanceStatuses attendances = new AttendanceStatuses(attendanceHistory, attendanceHistory.statusMap());
        AttendanceDismissStatus attendanceDismissStatus = attendances.calculateAttendanceDismiss();
        assertThat(attendanceDismissStatus).isEqualTo(AttendanceDismissStatus.DISMISS);
    }
}
