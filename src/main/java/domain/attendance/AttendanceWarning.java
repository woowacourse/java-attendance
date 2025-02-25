package domain.attendance;

import java.util.Arrays;

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

    public static AttendanceWarning determineAttendanceWarning(int absenceIncludingTardyCount) {
        return Arrays.stream(values())
                .filter(value -> value.absenceCount <= absenceIncludingTardyCount)
                .findFirst()
                .orElse(NONE);
    }

    public String getStatus() {
        return status;
    }
}
