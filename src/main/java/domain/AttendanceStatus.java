package domain;

import java.util.Map;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static CrewDismissCount createCrewDismissCount(Map<AttendanceStatus, Integer> crewAttendanceStatusCount) {
        int absence = crewAttendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0);
        int attendance = crewAttendanceStatusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0);
        int late = crewAttendanceStatusCount.getOrDefault(AttendanceStatus.LATE, 0);
        return new CrewDismissCount(absence, late, attendance);
    }

    public String getStatus() {
        return status;
    }
}

