package domain.attendance;

public enum AttendanceWarning {
    WEEDING("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("", 0),
    ;

    private final String status;
    private final int absenceCount;

    AttendanceWarning(String status, int absenceCount) {
        this.status = status;
        this.absenceCount = absenceCount;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceWarning determineAttendanceWarning(int absenceIncludingTardyCount) {
        for (AttendanceWarning value : values()) {
            if (value.absenceCount <= absenceIncludingTardyCount) {
                return value;
            }
        }
        return NONE;
    }
}
