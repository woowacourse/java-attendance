package domain;

import java.util.Map;

public enum ExpelStatus {

    WARNING("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적"),
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
        int absentCount = lateCount / 3 + attendanceStatuses.get(AttendanceStatus.ABSENT) + attendanceStatuses.get(AttendanceStatus.UNATTEND);
        return getExpelStatus(absentCount);
    }

    private static ExpelStatus getExpelStatus(int absentCount) {
        if (absentCount > 5) {
            return EXPULSION;
        }
        if (absentCount >= 3) {
            return INTERVIEW;
        }
        if (absentCount >= 2) {
            return WARNING;
        }
        return NONE;
    }
}
