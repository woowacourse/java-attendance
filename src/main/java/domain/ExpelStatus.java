package domain;

import constant.CampusConstant;
import java.util.Map;

public enum ExpelStatus {

    WARNING("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적"),
    NONE("미해당");

    private static final int EXPULSION_THRESHOLD = 6;
    private static final int INTERVIEW_THRESHOLD = 3;
    private static final int WARNING_THRESHOLD = 2;

    private final String expelStatus;

    ExpelStatus(String expelStatus) {
        this.expelStatus = expelStatus;
    }

    public String getExpelStatus() {
        return this.expelStatus;
    }

    public static ExpelStatus determineExpelStatus(Map<AttendanceStatus, Integer> attendanceStatuses) {
        int lateCount = attendanceStatuses.get(AttendanceStatus.LATE);
        int absentCount = lateCount / CampusConstant.LATE_TO_ABSENT_UNIT + attendanceStatuses.get(AttendanceStatus.ABSENT) + attendanceStatuses.get(AttendanceStatus.UNATTEND);
        return findByAbsentCount(absentCount);
    }

    private static ExpelStatus findByAbsentCount(int absentCount) {
        if (absentCount >= EXPULSION_THRESHOLD) {
            return EXPULSION;
        }
        if (absentCount >= INTERVIEW_THRESHOLD) {
            return INTERVIEW;
        }
        if (absentCount >= WARNING_THRESHOLD) {
            return WARNING;
        }
        return NONE;
    }
}
