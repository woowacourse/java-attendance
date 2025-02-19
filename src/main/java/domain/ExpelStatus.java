package domain;

import java.util.Map;

public enum ExpelStatus {

    WARNING, INTERVIEW, EXPULSION, NONE;

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
