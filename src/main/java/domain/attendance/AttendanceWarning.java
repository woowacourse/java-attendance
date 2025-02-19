package domain.attendance;

public enum AttendanceWarning {
    WEEDING("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("", 0),
    ;

    private final String status;
    private final int absenseCount;

    AttendanceWarning(String status, int absenseCount) {
        this.status = status;
        this.absenseCount = absenseCount;
    }

    public static AttendanceWarning determineAttendanceWarning(int absenceIncludingTardyCount) {
        for (AttendanceWarning value : values()) {
            if (value.absenseCount <= absenceIncludingTardyCount) {
                return value;
            }
        }
        return NONE;
    }
}
