package attendance.service;


import attendance.domain.AttendanceDismiss;
import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import java.util.List;
import java.util.Map;

public class CrewDismissService {

    private static final String CREW_DISMISS_PREFIX = "제적 위험자 조회 결과\n";
    private static final String CREW_DISMISS_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    private final AttendanceManager attendanceManager;

    public CrewDismissService(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public String formattingCrewDismiss() {
        StringBuilder stringBuilder = new StringBuilder(CREW_DISMISS_PREFIX);
        List<String> nicknames = attendanceManager.currentAttendancesNicknames();
        for (String nickname : nicknames) {
            AttendanceHistory attendanceHistory = attendanceManager.crewAttendanceHistory(nickname);
            stringBuilder.append(formattingCrewDismiss(nickname, attendanceHistory));
        }
        return stringBuilder.toString();
    }

    private String formattingCrewDismiss(String nickname, AttendanceHistory attendanceHistory) {
        Map<AttendanceStatus, Integer> status = attendanceHistory.status();
        int absenceCount = status.getOrDefault(AttendanceStatus.ABSENCE, 0);
        int lateCount = status.getOrDefault(AttendanceStatus.LATE, 0);
        AttendanceDismissStatus attendanceDismissStatus = calculateAttendanceStatus(absenceCount, lateCount);
        if (attendanceDismissStatus == AttendanceDismissStatus.NONE) {
            return "";
        }
        return String.format(CREW_DISMISS_FORMAT, nickname, absenceCount,
                lateCount, attendanceDismissStatus.getStatus());
    }

    private AttendanceDismissStatus calculateAttendanceStatus(int absenceCount, int lateCount) {
        return AttendanceDismiss.calculateAttendanceDismiss(absenceCount,
                lateCount);
    }
}
