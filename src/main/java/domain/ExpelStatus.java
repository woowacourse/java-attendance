package domain;

import java.util.Map;

public enum ExpelStatus {

    WARNING("경고 대상자"),
    INTERVIEW("면담 대상자"),
    EXPULSION("제적 대상자"),
    NONE("미해당");

    private final String expelStatus;

    ExpelStatus(String expelStatus) {
        this.expelStatus = expelStatus;
    }

    public String getExpelStatus() {
        return this.expelStatus;
    }

    public static ExpelStatus determineExpelStatus(Map<AttendanceStatus, Integer> attendanceStatuses) {
        int lateCount = attendanceStatuses.get(AttendanceStatus.LATE);
        int absentCount = lateCount / 3  + attendanceStatuses.get(AttendanceStatus.ABSENT) + attendanceStatuses.get(AttendanceStatus.UNATTEND);
        if (absentCount >= 5) {
            return EXPULSION;
        } else if (absentCount >= 3) {
            return INTERVIEW;
        } else if (absentCount >= 2) {
            return WARNING;
        }
        return NONE;
    }
}
