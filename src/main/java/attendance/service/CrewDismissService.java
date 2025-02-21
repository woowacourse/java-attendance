package attendance.service;


import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.domain.CrewDismiss;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrewDismissService {

    private static final String CREW_DISMISS_PREFIX = "제적 위험자 조회 결과\n";
    private static final String CREW_DISMISS_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    private final AttendanceManager attendanceManager;

    public CrewDismissService(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public String formattingCrewDismiss(List<String> nicknames) {
        StringBuilder stringBuilder = new StringBuilder(CREW_DISMISS_PREFIX);
        List<CrewDismiss> crewDismisses = nicknames.stream().map(nickname -> {
            Map<String, Integer> status = attendanceManager.crewAttendanceHistory(nickname).statusMap();
            String crewDismissResult = formattingCrewDismiss(nickname, status);
            stringBuilder.append(formattingCrewDismiss(nickname, status));
            return new CrewDismiss(nickname, crewDismissResult, status);
        }).toList();
        return crewDismisses.stream().sorted()
                .map((CrewDismiss::getCrewDismissResult))
                .collect(Collectors.joining("\n"));
    }

    private String formattingCrewDismiss(String nickname, Map<String, Integer> status) {
        int absenceCount = AttendanceStatus.absenceCount(status);
        int lateCount = AttendanceStatus.lateCount(status);
        AttendanceDismissStatus attendanceDismissStatus = calculateAttendanceStatus(absenceCount, lateCount);
        if (attendanceDismissStatus == AttendanceDismissStatus.NONE) {
            return "";
        }
        return String.format(CREW_DISMISS_FORMAT, nickname, absenceCount,
                lateCount, attendanceDismissStatus.getStatus());
    }

    private AttendanceDismissStatus calculateAttendanceStatus(int absenceCount, int lateCount) {
        return AttendanceDismissStatus.calculateAttendanceDismiss(absenceCount,
                lateCount);
    }
}
